package com.polarisdigitech.assessment.dispatch.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.TransactionSystemException;

import com.polarisdigitech.assessment.dispatch.dtos.AddItemDTO;
import com.polarisdigitech.assessment.dispatch.dtos.BoxDisplayDTO;
import com.polarisdigitech.assessment.dispatch.dtos.CreateBoxDTO;
import com.polarisdigitech.assessment.dispatch.entities.Box;
import com.polarisdigitech.assessment.dispatch.entities.Item;
import com.polarisdigitech.assessment.dispatch.enums.State;
import com.polarisdigitech.assessment.dispatch.exceptions.BoxBatteryLowException;
import com.polarisdigitech.assessment.dispatch.exceptions.FullBoxException;
import com.polarisdigitech.assessment.dispatch.repositories.BoxRepository;
import com.polarisdigitech.assessment.dispatch.repositories.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class BoxServiceTest {
    @Mock
    private BoxRepository boxRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private BoxService boxService;

    Box addTestBox() {
        Box box = new Box(
                "Tw1k3",
                (double) 500,
                34,
                State.IDLE);

        boxRepository.save(box);
        return box;
    }

    @Test
    void shouldCreateBoxSuccessfully() {
        CreateBoxDTO createBoxDTO = new CreateBoxDTO(
                "Tw1k3",
                (double) 500,
                34,
                State.IDLE);

        Box mockBox = new Box(
                "Tw1k3",
                (double) 500,
                34,
                State.IDLE);

        when(boxRepository.save(any(Box.class))).thenReturn(mockBox);

        BoxDisplayDTO serviceCreatedBox = boxService.createBox(createBoxDTO);

        assertEquals("Tw1k3", serviceCreatedBox.getTxRef());
        assertEquals(500, serviceCreatedBox.getWeightLimit(), 0.01);
        assertEquals(34, serviceCreatedBox.getBatteryCapacity());
        assertEquals(State.IDLE, serviceCreatedBox.getState());

        verify(boxRepository, times(1)).save(any(Box.class));
    }

    @Test
    void shouldThrowLowBatteryException() {
        CreateBoxDTO createBoxDTO = new CreateBoxDTO(
                "Tw1k3",
                (double) 500,
                24,
                State.LOADING);

        assertThrows(BoxBatteryLowException.class, () -> boxService.createBox(createBoxDTO));
    }

    @Test
    void shouldCreateBoxWithInvalidWeightThrowValidationException() {
        CreateBoxDTO createBoxDTO = new CreateBoxDTO(
                "Tw1k3",
                (double) 501,
                34,
                State.IDLE);

        when(boxRepository.save(any(Box.class))).thenThrow(TransactionSystemException.class);

        assertThrows(TransactionSystemException.class, () -> boxService.createBox(createBoxDTO));
        verify(boxRepository, times(1)).save(any(Box.class));
    }

    @Test
    void shouldCreateBoxWithInvalidBatteryCapacityThrowValidationException() {
        CreateBoxDTO createBoxDTO = new CreateBoxDTO(
                "Tw1k3",
                (double) 51,
                101,
                State.IDLE);

        when(boxRepository.save(any(Box.class))).thenThrow(TransactionSystemException.class);

        assertThrows(TransactionSystemException.class, () -> boxService.createBox(createBoxDTO));
        verify(boxRepository, times(1)).save(any(Box.class));
    }

    @Test
    void shouldGetBoxBatteryLevel() {
        Box box = addTestBox();
        Long boxId = box.getId();
        int expectedBatteryCapacity = 34;

        when(boxRepository.findbatteryCapacityById(boxId)).thenReturn(Optional.of(expectedBatteryCapacity));

        Optional<Integer> boxBatteryLevel = boxService.getBoxBatteryLevel(boxId);

        assertTrue(boxBatteryLevel.isPresent());
        assertEquals(expectedBatteryCapacity, boxBatteryLevel.get());

        verify(boxRepository, times(1)).findbatteryCapacityById(boxId);
    }

    @Test
    void shouldThrowFullBoxException() {
        Box box = addTestBox();

        when(boxRepository.findById(box.getId())).thenReturn(Optional.of(box));

        AddItemDTO addItemDTO = new AddItemDTO("FIRST_ITEM", 501, "ITM", box.getId());

        box.setItems(new HashSet<>());
        assertThrows(FullBoxException.class, () -> boxService.addItem(addItemDTO));

        verify(boxRepository, times(1)).findById(box.getId());
    }

    @Test
    void shouldReturnOneLoadableBox() {
        Box box = addTestBox();

        when(boxRepository.findBoxesByState(box.getState())).thenReturn(List.of(box));

        assertEquals(1, boxService.getLoadableBoxes().size());

        verify(boxRepository, times(1)).findBoxesByState(box.getState());
    }

    @Test
    void shouldAddItemToBox() {
        Box box = addTestBox();
        Item mockItem = new Item("FIRST_ITEM", 50, "ITM", box);

        when(boxRepository.findById(box.getId())).thenReturn(Optional.of(box));
        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        box.setItems(Set.of(mockItem));

        Box boxWithItems = boxService.addItem(new AddItemDTO("FIRST_ITEM", 50, "ITM", box.getId()));
        assertEquals(true, boxWithItems.getItems().contains(mockItem));

        verify(boxRepository, times(1)).findById(box.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void shouldAddItemWithInvalidWeightToBoxThrowException() {
        Box box = addTestBox();
        Item mockItem = new Item("FIRST_ITEM", 50, "ITM", box);

        when(boxRepository.findById(box.getId())).thenReturn(Optional.of(box));
        when(itemRepository.save(any(Item.class))).thenThrow(TransactionSystemException.class);

        box.setItems(Set.of(mockItem));

        assertThrows(TransactionSystemException.class, ()->boxService.addItem(new AddItemDTO("FIRST_ITEM", 50, "ITM", box.getId())));

        verify(boxRepository, times(1)).findById(box.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }
}
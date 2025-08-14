package com.polarisdigitech.assessment.dispatch.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarisdigitech.assessment.dispatch.dtos.AddItemDTO;
import com.polarisdigitech.assessment.dispatch.dtos.BoxDisplayDTO;
import com.polarisdigitech.assessment.dispatch.dtos.CreateBoxDTO;
import com.polarisdigitech.assessment.dispatch.entities.Box;
import com.polarisdigitech.assessment.dispatch.entities.Item;
import com.polarisdigitech.assessment.dispatch.enums.State;
import com.polarisdigitech.assessment.dispatch.exceptions.BoxBatteryLowException;
import com.polarisdigitech.assessment.dispatch.exceptions.BoxNotFoundException;
import com.polarisdigitech.assessment.dispatch.exceptions.FullBoxException;
import com.polarisdigitech.assessment.dispatch.repositories.BoxRepository;
import com.polarisdigitech.assessment.dispatch.repositories.ItemRepository;

@Service
public class BoxService {
    @Autowired
    private BoxRepository boxRepository;
    @Autowired
    private ItemRepository itemRepository;

    public BoxDisplayDTO createBox(CreateBoxDTO boxDTO) {
        if (boxDTO.getBatteryCapacity() < 25 && boxDTO.getState() == State.LOADING) {
            throw new BoxBatteryLowException("Battery is too low to be in LOADING state");
        }
        Box newBox = new Box(
                boxDTO.getTxRef(),
                boxDTO.getWeightLimit(),
                boxDTO.getBatteryCapacity(),
                boxDTO.getState());
        this.boxRepository.save(newBox);
        return new BoxDisplayDTO(newBox.getId(), newBox.getBatteryCapacity(), newBox.getWeightLimit(),
                newBox.getTxRef(), newBox.getState());
    }

    public Optional<Integer> getBoxBatteryLevel(Long id) {
        return boxRepository.findbatteryCapacityById(id);
    }

    public List<BoxDisplayDTO> getLoadableBoxes() {
        return boxRepository.findBoxesByState(State.IDLE).stream().map(box -> new BoxDisplayDTO(box.getId(),box.getBatteryCapacity(), box.getWeightLimit(), box.getTxRef(), box.getState())).collect(Collectors.toList());
    }

    public Box addItem(AddItemDTO addItemDTO) {
        Optional<Box> boxOptional = boxRepository.findById(addItemDTO.getBoxId());
        if (boxOptional.isEmpty()) {
            throw new BoxNotFoundException("Box not found");
        }
        Box box = boxOptional.get();
        Double boxWeightLimit = box.getWeightLimit();

        Integer boxCurrentWeight = box.getItems().stream().mapToInt(Item::getWeight).sum();
        if (addItemDTO.getWeight() + boxCurrentWeight > boxWeightLimit) {
            throw new FullBoxException("The box cannot carry an item with this weight as it will exceed capacity");
        }
        Item newItem = new Item(
                addItemDTO.getName(),
                addItemDTO.getWeight(),
                addItemDTO.getCode(),
                box);

        itemRepository.save(newItem);
        return box;
    }

    public List<Item> getItems(Long id) {
        return itemRepository.findAllByBoxId(id);
    }
}

package com.polarisdigitech.assessment.dispatch.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.polarisdigitech.assessment.dispatch.dtos.AddItemDTO;
import com.polarisdigitech.assessment.dispatch.dtos.CreateBoxDTO;
import com.polarisdigitech.assessment.dispatch.responses.ResponseHandler;
import com.polarisdigitech.assessment.dispatch.services.BoxService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("boxes")
public class BoxController {
    @Autowired
    private BoxService boxService;

    @PostMapping
    public ResponseEntity<Object> createBox(@Valid @RequestBody CreateBoxDTO createBoxDTO) {
        return ResponseHandler.generateResponse("New box Created", HttpStatus.CREATED, boxService.createBox(createBoxDTO));
    }

    @GetMapping("{id}/battery")
    public ResponseEntity<Object> getBoxBattery(@PathVariable Long id) {
        return ResponseHandler.generateResponse("Box battery retrieved", HttpStatus.OK, boxService.getBoxBatteryLevel(id)) ;
    }

    @GetMapping("/loadable")
    public ResponseEntity<Object> getLoadableBoxes() {
        return ResponseHandler.generateResponse("All loadable boxes retrieved", HttpStatus.OK, boxService.getLoadableBoxes());
    }

    @GetMapping("{id}/items")
    public ResponseEntity<Object> getItems(@PathVariable Long id) {
        return ResponseHandler.generateResponse("All items on box %d".formatted(id), HttpStatus.OK,boxService.getItems(id));
    }
    
    @PostMapping("{id}/items")
    public ResponseEntity<Object> addItem(@Valid @RequestBody AddItemDTO addItemDTO) {
        return ResponseHandler.generateResponse("New item added to box %d".formatted(addItemDTO.getBoxId()), HttpStatus.CREATED,boxService.addItem(addItemDTO));
    }
}

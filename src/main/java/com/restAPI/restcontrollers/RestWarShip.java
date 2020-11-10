package com.restAPI.restcontrollers;

import com.restAPI.entities.WarShipDTO;
import com.restAPI.services.WarShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/warships")
public class RestWarShip {

    private final WarShipService warShipService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getWarShips() throws Exception {
        return new ResponseEntity<>(warShipService.getWarShips(), OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getWarShipDetails(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(warShipService.getOneWarShip(id), OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addWarShip(@RequestBody WarShipDTO warShipDTO) throws Exception {
        warShipService.addWarShip(warShipDTO);
        return new ResponseEntity<>("War ship saved successfully", CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateWarShipDetails(@PathVariable Long id, @RequestBody WarShipDTO warShipDTO) throws Exception {
        warShipDTO.setId(id);
        warShipService.updateWarShip(warShipDTO);
        return new ResponseEntity<>("War ship updated successfully", OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> removeWarship(@PathVariable Long id) throws Exception {
        warShipService.removeWarShip(id);
        return new ResponseEntity<>("War ship deleted successfully", OK);
    }
}
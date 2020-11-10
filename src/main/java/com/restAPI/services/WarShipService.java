package com.restAPI.services;

import com.restAPI.daos.WarShipDAO;
import com.restAPI.entities.WarShip;
import com.restAPI.entities.WarShipDTO;
import com.restAPI.exceptionhandler.CustomServiceException;
import com.restAPI.utils.ArmorType;
import com.restAPI.utils.ShipType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class WarShipService {

    private final WarShipDAO warShipDAO;

    public List<WarShipDTO> getWarShips() throws CustomServiceException {
        try {
            List<WarShipDTO> warShipDTOS = new ArrayList<>();
            for (WarShip warShip: warShipDAO.findAll()) {
                warShipDTOS.add(new WarShipDTO(warShip));
            }
            return warShipDTOS;
        } catch (Exception e) {
            throw new CustomServiceException("Data Source issue, could not get the war ships", INTERNAL_SERVER_ERROR);
        }
    }

    public void addWarShip(WarShipDTO warShipDTO) throws CustomServiceException {
        validateWarShip(warShipDTO);
        try {
            warShipDAO.save(new WarShip(warShipDTO));
        } catch (Exception e) {
            throw new CustomServiceException("Data Source issue, war ship could not be saved", INTERNAL_SERVER_ERROR);
        }
    }

    public void removeWarShip(Long id) throws CustomServiceException {
        try {
            warShipDAO.deleteById(id);
        } catch (Exception e) {
            throw new CustomServiceException("Data Source issue, could not delete war ship", INTERNAL_SERVER_ERROR);
        }
    }

    public WarShipDTO getOneWarShip(Long id) throws CustomServiceException {
        Optional<WarShip> optionalWarShip = warShipDAO.findById(id);
        if (optionalWarShip.isPresent()) {
            return new WarShipDTO(optionalWarShip.get());
        }
        throw new CustomServiceException("The war ship could not be found", INTERNAL_SERVER_ERROR);
    }

    public void updateWarShip(WarShipDTO warShipDTO) throws CustomServiceException {
        Optional<WarShip> optionalWarShip = warShipDAO.findById(warShipDTO.getId());
        warShipPresent(optionalWarShip);
        validateWarShip(warShipDTO);
        WarShip toBeUpdated = optionalWarShip.get();

        toBeUpdated.setName(warShipDTO.getName());
        toBeUpdated.setShipType(ShipType.valueOf(warShipDTO.getShipType()));
        toBeUpdated.setFirePower(warShipDTO.getFirePower());
        toBeUpdated.setRange(warShipDTO.getRange());
        toBeUpdated.setArmorType(ArmorType.valueOf(warShipDTO.getArmorType()));

        try {
            warShipDAO.save(toBeUpdated);
        } catch (Exception e) {
            throw new CustomServiceException("Data Source issue, war ship could not be updated", INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateWarShip(WarShipDTO warShipDTO) throws CustomServiceException {
        try {
            if (validateName(warShipDTO.getName())) {
                validateShipType(warShipDTO.getShipType());
                validateFirePower(warShipDTO.getFirePower());
                validateRange(warShipDTO.getRange());
                validateArmorType(warShipDTO.getArmorType());
                return true;
            }
            else {
                throw new CustomServiceException("You are trying to set an invalid ship name", BAD_REQUEST);
            }
        } catch (NullPointerException e) {
            throw new CustomServiceException("Null pointer exception", BAD_REQUEST);
        }
    }

    private boolean validateName(String name) {
        return !name.isEmpty() && !name.contains(" ") && name.length() >= 1 && !name.matches(".*\\d.*");
    }

    private void validateShipType(String shipType) throws CustomServiceException {
        List shipTypes = Arrays.asList(ShipType.values());
        int count = 0;
        if(shipTypes.contains(ShipType.valueOf(shipType))){
            count++;
        }
        if(count == 1) {
            return;
        }
        throw new CustomServiceException("You are trying to set an invalid ship type \n" +
                "Use only Aircraft_Carrier, Battlecruiser, Battleship, Destroyer or Cruiser", BAD_REQUEST);
    }

    private void validateFirePower(Integer firePower) throws CustomServiceException {
        if (firePower > 0 && firePower <= 1000) {
            return;
        }
        throw new CustomServiceException("You are trying to set an invalid fire power", BAD_REQUEST);
    }

    private void validateRange(Integer range) throws CustomServiceException {
        if (range > 0 && range < 10000) {
            return;
        }
        throw new CustomServiceException("You are trying to set an invalid range", BAD_REQUEST);
    }

    private void validateArmorType(String armorType) throws CustomServiceException {
        List armorTypes = Arrays.asList(ArmorType.values());
        int count = 0;
        if(armorTypes.contains(ArmorType.valueOf(armorType))){
            count++;
        }
        if(count == 1) {
            return;
        }
        throw new CustomServiceException("You are trying to set an invalid armor type \n" +
                "Use only Light, Medium or Heavy", BAD_REQUEST);
    }

    private void warShipPresent(Optional<WarShip> optionalWarShip) throws CustomServiceException {
        if (optionalWarShip.isPresent()) {
            return;
        }
        throw new CustomServiceException("War ship not found", INTERNAL_SERVER_ERROR);
    }




}

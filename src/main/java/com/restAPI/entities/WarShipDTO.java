package com.restAPI.entities;

import lombok.*;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class WarShipDTO {

    private Long id;
    private String name;
    private String shipType;
    private Integer firePower;
    private Integer range;
    private String armorType;

    public WarShipDTO (WarShip warShip) {
        this.id = warShip.getId();
        this.name = warShip.getName();
        this.shipType = warShip.getShipType().name();
        this.firePower = warShip.getFirePower();
        this.range = warShip.getRange();
        this.armorType = warShip.getArmorType().name();
    }
}

package com.restAPI.entities;

import com.restAPI.utils.ArmorType;
import com.restAPI.utils.ShipType;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "war_ship")
public class WarShip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "ship_type")
    private ShipType shipType;

    @NonNull
    @Column(name = "fire_power")
    private Integer firePower;

    @NonNull
    @Column(name = "range")
    private Integer range;

    @NonNull
    @Column(name = "armor_type")
    private ArmorType armorType;

    public WarShip(WarShipDTO warShipDTO) {
        this.id = warShipDTO.getId();
        this.name = warShipDTO.getName();
        this.shipType = ShipType.valueOf(warShipDTO.getShipType());
        this.firePower = warShipDTO.getFirePower();
        this.range = warShipDTO.getRange();
        this.armorType = ArmorType.valueOf(warShipDTO.getArmorType());
    }
}

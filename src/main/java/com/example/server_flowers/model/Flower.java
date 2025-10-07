package com.example.server_flowers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Flower {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String name;
    private String botanicalName;
    private String plantType;
    private String bloomTime;
    private String meaning;


    public Flower(String name, String botanicalName, String plantType, String bloomTime, String meaning) {
        this.name = name;
        this.botanicalName = botanicalName;
        this.plantType = plantType;
        this.bloomTime = bloomTime;
        this.meaning = meaning;
    }
}

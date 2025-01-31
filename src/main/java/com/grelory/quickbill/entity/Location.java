package com.grelory.quickbill.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String locationId;

    @Column(unique=true, nullable=false)
    private String locationName;

}

package com.grelory.quickbill.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TransportType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transportTypeId;

    @Column(unique=true, nullable=false)
    private String transportTypeName;

}

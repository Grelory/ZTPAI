package com.grelory.quickbill.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String providerId;

    @Column(unique=true, nullable=false)
    private String providerName;

}

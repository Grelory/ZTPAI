package com.grelory.quickbill.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Entity
@Getter
@Setter
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketTypeId;

    @Column(unique=true, nullable=false)
    private String ticketTypeName;

    @Column
    private Integer ticketTypeExpiryNumber;

    @Column
    private String ticketTypeExpiryUnit;

}

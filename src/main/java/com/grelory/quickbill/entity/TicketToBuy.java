package com.grelory.quickbill.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"provider_id", "location_id", "transport_type_id", "ticket_type_id"}
))
public class TicketToBuy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketToBuyId;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "transport_type_id", nullable = false)
    private TransportType transportType;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    public TicketToBuy() {}

    public TicketToBuy(
            String ticketToBuyId,
            Provider provider,
            Location location,
            TransportType transportType,
            TicketType ticketType) {
        this.ticketToBuyId = ticketToBuyId;
        this.provider = provider;
        this.location = location;
        this.transportType = transportType;
        this.ticketType = ticketType;
    }
}

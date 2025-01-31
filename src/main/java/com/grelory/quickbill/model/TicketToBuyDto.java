package com.grelory.quickbill.model;

import com.grelory.quickbill.entity.TicketToBuy;

public record TicketToBuyDto(
        String ticketToBuyId,
        String providerName,
        String locationName,
        String transportTypeName,
        String ticketTypeName) {

    public static TicketToBuyDto from(TicketToBuy ticketToBuy) {
        return new TicketToBuyDto(
                ticketToBuy.getTicketToBuyId(),
                ticketToBuy.getProvider().getProviderName(),
                ticketToBuy.getLocation().getLocationName(),
                ticketToBuy.getTransportType().getTransportTypeName(),
                ticketToBuy.getTicketType().getTicketTypeName()
        );
    }
}

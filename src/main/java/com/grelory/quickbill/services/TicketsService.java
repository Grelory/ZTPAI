package com.grelory.quickbill.services;

import com.grelory.quickbill.entity.TicketToBuy;
import com.grelory.quickbill.model.TicketToBuyDto;
import com.grelory.quickbill.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketsService {

    private final LocationsRepository locationsRepository;
    private final ProvidersRepository providersRepository;
    private final TransportTypesRepository transportTypesRepository;
    private final TicketTypesRepository ticketTypesRepository;
    private final TicketsToBuyRepository ticketsToBuyRepository;

    public TicketsService(
            @Autowired LocationsRepository locationsRepository,
            @Autowired ProvidersRepository providersRepository,
            @Autowired TransportTypesRepository transportTypesRepository,
            @Autowired TicketTypesRepository ticketTypesRepository,
            @Autowired TicketsToBuyRepository ticketsToBuyRepository) {
        this.locationsRepository = locationsRepository;
        this.providersRepository = providersRepository;
        this.transportTypesRepository = transportTypesRepository;
        this.ticketTypesRepository = ticketTypesRepository;
        this.ticketsToBuyRepository = ticketsToBuyRepository;
    }

    public List<TicketToBuyDto> allUnmatchedTicketsToBuy() {
        return ticketsToBuyRepository.findAllUnmatched()
                .stream()
                .map(TicketToBuyDto::from)
                .collect(Collectors.toList());
    }

    public List<TicketToBuyDto> allTicketsToBuy() {
        List<TicketToBuyDto> dtos = new ArrayList<>();
        ticketsToBuyRepository.findAll().forEach(ticketToBuy -> dtos.add(TicketToBuyDto.from(ticketToBuy)));
        return dtos;
    }

    public void saveTicketToBuy(
            String providerName,
            String locationName,
            String transportTypeName,
            String ticketTypeName) {
        TicketToBuy newTicketToBuy = new TicketToBuy(
                null,
                providersRepository.findByProviderName(providerName),
                locationsRepository.findByLocationName(locationName),
                transportTypesRepository.findByTransportTypeName(transportTypeName),
                ticketTypesRepository.findByTicketTypeName(ticketTypeName)
        );
        ticketsToBuyRepository.save(newTicketToBuy);
    }
}

package com.grelory.quickbill.services;

import com.grelory.quickbill.entity.*;
import com.grelory.quickbill.model.TicketToBuyDto;
import com.grelory.quickbill.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminViewService {

    private final LocationsRepository locationsRepository;
    private final ProvidersRepository providersRepository;
    private final TransportTypesRepository transportTypesRepository;
    private final TicketTypesRepository ticketTypesRepository;
    private final TicketsToBuyRepository ticketsToBuyRepository;

    public AdminViewService(
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

    public List<Location> allLocations() {
        List<Location> locations = new ArrayList<>();
        locationsRepository.findAll().forEach(locations::add);
        return locations;
    }

    public void saveLocation(String locationName) {
        Location newLocation = new Location();
        newLocation.setLocationName(locationName);
        locationsRepository.save(newLocation);
    }

    public List<Provider> allProviders() {
        List<Provider> providers = new ArrayList<>();
        providersRepository.findAll().forEach(providers::add);
        return providers;
    }

    public void saveProvider(String providerName) {
        Provider newProvider = new Provider();
        newProvider.setProviderName(providerName);
        providersRepository.save(newProvider);
    }

    public List<TransportType> allTransportTypes() {
        List<TransportType> transportTypes = new ArrayList<>();
        transportTypesRepository.findAll().forEach(transportTypes::add);
        return transportTypes;
    }

    public void saveTransportType(String transportTypeName) {
        TransportType newTransportType = new TransportType();
        newTransportType.setTransportTypeName(transportTypeName);
        transportTypesRepository.save(newTransportType);
    }

    public List<String> allExpiryUnits() {
        return Arrays.stream(ExpiryUnit.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public List<TicketType> allTicketTypes() {
        List<TicketType> ticketTypes = new ArrayList<>();
        ticketTypesRepository.findAll().forEach(ticketTypes::add);
        return ticketTypes;
    }

    public void saveTicketType(
            String ticketTypeName,
            Integer ticketTypeExpiryNumber,
            String ticketTypeExpiryUnit) {
        TicketType newTicketType = new TicketType();
        newTicketType.setTicketTypeName(ticketTypeName);
        newTicketType.setTicketTypeExpiryNumber(ticketTypeExpiryNumber);
        newTicketType.setTicketTypeExpiryUnit(ticketTypeExpiryUnit);
        ticketTypesRepository.save(newTicketType);
    }
}

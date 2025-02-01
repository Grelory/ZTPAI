package com.grelory.quickbill.controllers;

import com.grelory.quickbill.model.TicketToBuyDto;
import com.grelory.quickbill.services.TicketsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources/tickets")
@Tag(name = "Ticket Resource", description = "APIs related to ticket operations")
public class TicketResource {

    private final TicketsService ticketsService;

    public TicketResource(@Autowired TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    @GetMapping("/to-buy")
    @Operation(summary = "Get all tickets to buy", description = "Retrieve a list of all tickets available for purchase")
    public List<TicketToBuyDto> ticketToBuyDtos() {
        return ticketsService.allTicketsToBuy();
    }

    @GetMapping("/unmatched-to-buy")
    @Operation(summary = "Get all unmatched tickets to buy", description = "Retrieve a list of all unmatched tickets available for purchase")
    public List<TicketToBuyDto> unmatchedTicketToBuyDtos() {
        return ticketsService.allUnmatchedTicketsToBuy();
    }
}

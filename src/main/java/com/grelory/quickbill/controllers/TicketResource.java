package com.grelory.quickbill.controllers;

import com.grelory.quickbill.model.TicketToBuyDto;
import com.grelory.quickbill.services.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources/tickets")
public class TicketResource {

    private final TicketsService ticketsService;

    public TicketResource(@Autowired TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    @GetMapping("/to-buy")
    public List<TicketToBuyDto> ticketToBuyDtos() {
        return ticketsService.allTicketsToBuy();
    }

    @GetMapping("/unmatched-to-buy")
    public List<TicketToBuyDto> unmatchedTicketToBuyDtos() {
        return ticketsService.allUnmatchedTicketsToBuy();
    }


}

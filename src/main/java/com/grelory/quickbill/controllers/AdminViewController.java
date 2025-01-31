package com.grelory.quickbill.controllers;

import com.grelory.quickbill.services.AdminViewService;
import com.grelory.quickbill.services.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final AdminViewService adminViewService;
    private final TicketsService ticketsService;

    public AdminViewController(
            @Autowired AdminViewService adminViewService,
            @Autowired TicketsService ticketsService) {
        this.adminViewService = adminViewService;
        this.ticketsService = ticketsService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "admin/dashboard";
    }

    @GetMapping("/tickets")
    public String tickets(Model model) {
        model.addAttribute("items", ticketsService.allTicketsToBuy());
        return "admin/tickets";
    }

    @PostMapping("/tickets")
    public RedirectView postTickets(
            @RequestParam("provider") String providerName,
            @RequestParam("location") String locationName,
            @RequestParam("transport-type") String transportTypeName,
            @RequestParam("ticket-type") String ticketTypeName) {
        ticketsService.saveTicketToBuy(providerName, locationName, transportTypeName, ticketTypeName);
        return new RedirectView("tickets");
    }

    @GetMapping("/providers")
    public String providers(Model model) {
        model.addAttribute("items", adminViewService.allProviders());
        return "admin/providers";
    }

    @PostMapping("/providers")
    public RedirectView postProviders(@RequestParam("provider") String provider) {
        adminViewService.saveProvider(provider);
        return new RedirectView("providers");
    }

    @GetMapping("/locations")
    public String locations(Model model) {
        model.addAttribute("items", adminViewService.allLocations());
        return "admin/locations";
    }

    @PostMapping("/locations")
    public RedirectView postLocations(@RequestParam("location") String location) {
        adminViewService.saveLocation(location);
        return new RedirectView("locations");
    }

    @GetMapping("/transport")
    public String transport(Model model) {
        model.addAttribute("items", adminViewService.allTransportTypes());
        return "admin/transport";
    }

    @PostMapping("/transport")
    public RedirectView postTransport(@RequestParam("transport-type") String transportType) {
        adminViewService.saveTransportType(transportType);
        return new RedirectView("transport");
    }

    @GetMapping("/types")
    public String types(Model model) {
        model.addAttribute("intervals", adminViewService.allExpiryUnits());
        model.addAttribute("types", adminViewService.allTicketTypes());
        return "admin/types";
    }

    @PostMapping("/types")
    public RedirectView postTypes(
            @RequestParam("ticket-type") String ticketType,
            @RequestParam("interval-number") Integer ticketTypeExpiryNumber,
            @RequestParam("interval-name") String ticketTypeExpiryUnit) {
        adminViewService.saveTicketType(ticketType, ticketTypeExpiryNumber, ticketTypeExpiryUnit);
        return new RedirectView("types");
    }
}

package com.grelory.quickbill.controllers;

import com.grelory.quickbill.services.AdminViewService;
import com.grelory.quickbill.services.TicketsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
@Tag(name = "Admin View Controller", description = "APIs for admin view operations")
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
    @Operation(summary = "Get dashboard", description = "Retrieve the admin dashboard view")
    public String dashboard(@CookieValue("jwt_token") String token, Model model) {
        model.addAttribute("name", "Admin");
        return "admin/dashboard";
    }

    @GetMapping("/tickets")
    @Operation(summary = "Get tickets", description = "Retrieve all tickets to buy")
    public String tickets(Model model) {
        model.addAttribute("items", ticketsService.allTicketsToBuy());
        return "admin/tickets";
    }

    @PostMapping("/tickets")
    @Operation(summary = "Post tickets", description = "Save a new ticket to buy")
    public RedirectView postTickets(
            @RequestParam("provider") String providerName,
            @RequestParam("location") String locationName,
            @RequestParam("transport-type") String transportTypeName,
            @RequestParam("ticket-type") String ticketTypeName) {
        ticketsService.saveTicketToBuy(providerName, locationName, transportTypeName, ticketTypeName);
        return new RedirectView("/admin/tickets");
    }

    @GetMapping("/providers")
    @Operation(summary = "Get providers", description = "Retrieve all providers")
    public String providers(Model model) {
        model.addAttribute("items", adminViewService.allProviders());
        return "admin/providers";
    }

    @PostMapping("/providers")
    @Operation(summary = "Post providers", description = "Save a new provider")
    public RedirectView postProviders(@RequestParam("provider") String provider) {
        adminViewService.saveProvider(provider);
        return new RedirectView("/admin/providers");
    }

    @GetMapping("/locations")
    @Operation(summary = "Get locations", description = "Retrieve all locations")
    public String locations(Model model) {
        model.addAttribute("items", adminViewService.allLocations());
        return "admin/locations";
    }

    @PostMapping("/locations")
    @Operation(summary = "Post locations", description = "Save a new location")
    public RedirectView postLocations(@RequestParam("location") String location) {
        adminViewService.saveLocation(location);
        return new RedirectView("/admin/locations");
    }

    @GetMapping("/transport")
    @Operation(summary = "Get transport types", description = "Retrieve all transport types")
    public String transport(Model model) {
        model.addAttribute("items", adminViewService.allTransportTypes());
        return "admin/transport";
    }

    @PostMapping("/transport")
    @Operation(summary = "Post transport types", description = "Save a new transport type")
    public RedirectView postTransport(@RequestParam("transport-type") String transportType) {
        adminViewService.saveTransportType(transportType);
        return new RedirectView("/admin/transport");
    }

    @GetMapping("/types")
    @Operation(summary = "Get ticket types", description = "Retrieve all ticket types and expiry units")
    public String types(Model model) {
        model.addAttribute("intervals", adminViewService.allExpiryUnits());
        model.addAttribute("types", adminViewService.allTicketTypes());
        return "admin/types";
    }

    @PostMapping("/types")
    @Operation(summary = "Post ticket types", description = "Save a new ticket type")
    public RedirectView postTypes(
            @RequestParam("ticket-type") String ticketType,
            @RequestParam("interval-number") Integer ticketTypeExpiryNumber,
            @RequestParam("interval-name") String ticketTypeExpiryUnit) {
        adminViewService.saveTicketType(ticketType, ticketTypeExpiryNumber, ticketTypeExpiryUnit);
        return new RedirectView("/admin/types");
    }
}

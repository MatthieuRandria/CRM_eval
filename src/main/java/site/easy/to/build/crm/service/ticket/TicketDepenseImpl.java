package site.easy.to.build.crm.service.ticket;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.LeadDepense;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.TicketDepense;
import site.easy.to.build.crm.repository.TicketDepenseRepository;
import site.easy.to.build.crm.repository.TicketRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TicketDepenseImpl implements TicketDepenseService {
    private TicketDepenseRepository ticketDepenseRepository;
    private TicketRepository ticketRepository;

    public TicketDepenseImpl(TicketDepenseRepository ticketDepenseRepository, TicketRepository ticketRepository) {
        this.ticketDepenseRepository = ticketDepenseRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketDepense findById(int id) {
        return ticketDepenseRepository.findById(id);
    }

    @Override
    public TicketDepense findByTicketId(int ticketId) {
        return ticketDepenseRepository.findByTicketTicketId(ticketId);
    }

    @Override
    public List<TicketDepense> findByCustomerId(int customerId) {
        List<Ticket> tickets = ticketRepository.findByCustomerCustomerId(customerId);
        if (tickets.isEmpty()) {
            return Collections.emptyList();
        }
        return tickets.stream()
                .map(ticket -> ticketDepenseRepository.findByTicketTicketId(ticket.getTicketId())) // Retourne un seul objet
                .filter(Objects::nonNull) // Évite les valeurs null
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDepense> findAll() {
        return ticketDepenseRepository.findAll();
    }

    @Override
    public double getSumTicketDepense(int customerId){
        List<TicketDepense> ticketDepenses = this.findByCustomerId(customerId);
        if (ticketDepenses.isEmpty()) return 0;
        return ticketDepenses.stream().mapToDouble(TicketDepense::getMontant).sum();
    }

    @Override
    public void save(TicketDepense ticketDepense) {
        this.ticketDepenseRepository.save(ticketDepense);
    }

    @Override
    public void delete(TicketDepense ticketDepense) {
        this.ticketDepenseRepository.delete(ticketDepense);
    }

    @Override
    public void update(int id,double montant) {
        this.ticketDepenseRepository.updateTicketDepenseById(id,montant);
    }
}

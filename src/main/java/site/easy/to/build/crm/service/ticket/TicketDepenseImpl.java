package site.easy.to.build.crm.service.ticket;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.LeadDepense;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.TicketDepense;
import site.easy.to.build.crm.repository.TicketDepenseRepository;
import site.easy.to.build.crm.repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;

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
        List<TicketDepense> ticketDepenses=new ArrayList<>();
        if (tickets.isEmpty() || tickets.size()==0 || tickets!=null) {
            for (Ticket ticket : tickets) {
                ticketDepenses.add(ticketDepenseRepository.findByTicketTicketId(ticket.getTicketId()));
            }
        }
        return ticketDepenses;
    }

    @Override
    public double getSumTicketDepense(int customerId){
        double res=0;
        List<TicketDepense> tickets = findByCustomerId(customerId);
        if (tickets.isEmpty() || tickets.size()==0 || tickets!=null) {
            for (TicketDepense ticketDepense : tickets) {
                res+=ticketDepense.getMontant();
            }
        }
        return res;
    }

    @Override
    public void save(TicketDepense ticketDepense) {
        this.ticketDepenseRepository.save(ticketDepense);
    }
}

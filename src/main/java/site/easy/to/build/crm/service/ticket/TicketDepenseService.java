package site.easy.to.build.crm.service.ticket;

import site.easy.to.build.crm.entity.TicketDepense;

import java.util.List;

public interface TicketDepenseService {
    public TicketDepense findById(int ticketId);
    public TicketDepense findByTicketId(int ticketId);
    public List<TicketDepense> findByCustomerId(int customerId);
    public double getSumTicketDepense(int customerId);
    public void save(TicketDepense ticketDepense);
}

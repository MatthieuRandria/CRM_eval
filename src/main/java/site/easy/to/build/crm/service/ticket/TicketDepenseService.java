package site.easy.to.build.crm.service.ticket;

import site.easy.to.build.crm.entity.TicketDepense;

import java.util.List;

public interface TicketDepenseService {
    public TicketDepense findById(int ticketId);
    public TicketDepense findByTicketId(int ticketId);
    public List<TicketDepense> findByCustomerId(int customerId);
    public List<TicketDepense> findAll();
    public double getSumTicketDepense(int customerId);
    public void save(TicketDepense ticketDepense);
    public void delete(TicketDepense ticketDepense);
    public void update(int id,double value);
}

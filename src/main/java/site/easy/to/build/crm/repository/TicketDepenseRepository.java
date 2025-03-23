package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.TicketDepense;

import java.util.List;

@Repository
public interface TicketDepenseRepository extends JpaRepository<TicketDepense, Integer> {
    public TicketDepense findById(int id);
    public TicketDepense findByTicketTicketId(int ticketId);
}

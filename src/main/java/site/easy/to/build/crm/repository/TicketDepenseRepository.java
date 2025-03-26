package site.easy.to.build.crm.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.TicketDepense;

import java.util.List;

@Repository
public interface TicketDepenseRepository extends JpaRepository<TicketDepense, Integer> {
    public TicketDepense findById(int id);
    public TicketDepense findByTicketTicketId(int ticketId);
    public List<TicketDepense> findAll();
    public void delete(TicketDepense ticketDepense);

    @Modifying
    @Transactional
    @Query("UPDATE TicketDepense td SET td.montant = :montant WHERE td.id = :id")

        public int updateTicketDepenseById(int id, double montant);
}

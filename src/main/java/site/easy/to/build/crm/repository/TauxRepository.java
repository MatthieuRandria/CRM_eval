package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.TicketDepense;
import site.easy.to.build.crm.entity.settings.Taux;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface TauxRepository extends JpaRepository<Taux, Integer> {
    // Get the N most recent values
    @Query("SELECT t FROM Taux t ORDER BY t.created_at DESC")
    List<Taux> findRecentTaux();

    @Query("SELECT t FROM Taux t ORDER BY t.created_at DESC limit 1")
    Taux findOne(Integer id);
}
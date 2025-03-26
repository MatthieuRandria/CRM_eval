package site.easy.to.build.crm.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.LeadDepense;

import java.util.List;

@Repository
public interface LeadDepenseRepository extends JpaRepository<LeadDepense, Integer> {

    public List<LeadDepense> findById(int leadId);
    public List<LeadDepense> findAll();
    public LeadDepense findByLeadLeadId(int leadId);
    public void deleteById(int leadId);

    @Modifying
    @Transactional
    @Query("UPDATE LeadDepense td SET td.montant = :montant WHERE td.id = :id")
    int updateLeadDepenseById(int id, double montant);
}

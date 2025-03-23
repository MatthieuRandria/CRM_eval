package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.LeadDepense;

import java.util.List;

@Repository
public interface LeadDepenseRepository extends JpaRepository<LeadDepense, Integer> {

    public List<LeadDepense> findById(int leadId);
    public LeadDepense findByLeadLeadId(int leadId);
}

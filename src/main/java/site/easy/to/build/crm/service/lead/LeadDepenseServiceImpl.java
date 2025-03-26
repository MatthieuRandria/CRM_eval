package site.easy.to.build.crm.service.lead;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.LeadDepense;
import site.easy.to.build.crm.repository.LeadDepenseRepository;
import site.easy.to.build.crm.repository.LeadRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LeadDepenseServiceImpl implements LeadDepenseService {
    private LeadDepenseRepository leadDepenseRepository;
    private LeadRepository leadRepository;

    public LeadDepenseServiceImpl(LeadDepenseRepository leadDepenseRepository, LeadRepository leadRepository) {
        this.leadDepenseRepository = leadDepenseRepository;
        this.leadRepository = leadRepository;
    }

    @Override
    public List<LeadDepense> findById(int leadId) {
        return leadDepenseRepository.findById(leadId);
    }

    @Override
    public List<LeadDepense> findByCustomerId(int customerId) {
        List<Lead> leads = leadRepository.findByCustomerCustomerId(customerId);
        if (leads.isEmpty()) {
            return Collections.emptyList();
        }
        return leads.stream()
                .map(lead -> leadDepenseRepository.findByLeadLeadId(lead.getLeadId())) // Retourne un seul objet
                .filter(Objects::nonNull) // Évite les valeurs null
                .collect(Collectors.toList());
    }

    @Override
    public List<LeadDepense> findAll() {
        return this.leadDepenseRepository.findAll();
    }

    @Override
    public LeadDepense findByLeadId(int leadId) {
        return leadDepenseRepository.findByLeadLeadId(leadId);
    }


    @Override
    public double getSumDepensesCustomerLeads(int customerId) {
        List<LeadDepense> leads = this.findByCustomerId(customerId);
        if (leads.isEmpty()) return 0;
        return leads.stream().mapToDouble(LeadDepense::getMontant).sum();
    }

    @Override
    public void save(LeadDepense leadDepense) {
        this.leadDepenseRepository.save(leadDepense);
    }

    @Override
    public void delete(LeadDepense byLeadId) {
        this.leadDepenseRepository.delete(byLeadId);
    }


    @Override
    public void update(int id, double montant) {
        this.leadDepenseRepository.updateLeadDepenseById(id, montant);
    }

}

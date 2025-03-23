package site.easy.to.build.crm.service.lead;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.LeadDepense;
import site.easy.to.build.crm.repository.LeadDepenseRepository;
import site.easy.to.build.crm.repository.LeadRepository;

import java.util.ArrayList;
import java.util.List;

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
        List<Lead>leads=leadRepository.findByCustomerCustomerId(customerId);
        List<LeadDepense> leadDepenses=new ArrayList<>();
        if (leads.isEmpty() || leads.size()==0 || leads!=null) {
            for (Lead lead : leads) {
                leadDepenses.add(leadDepenseRepository.findByLeadLeadId(lead.getLeadId()));
            }
        }
        return leadDepenses;
    }

    @Override
    public LeadDepense findByLeadId(int leadId) {
        return leadDepenseRepository.findByLeadLeadId(leadId);
    }


    @Override
    public double getSumDepensesCustomerLeads(int customerId) {
        double res=0;
        List<LeadDepense>leads=this.findByCustomerId(customerId);
        if (!leads.isEmpty() || leads.size()>0 || leads!=null){
            for (LeadDepense lead:leads) {
                res+=lead.getMontant();
            }
        }
        return res;
    }

    @Override
    public void save(LeadDepense leadDepense) {
        this.leadDepenseRepository.save(leadDepense);
    }

}

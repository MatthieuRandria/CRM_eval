package site.easy.to.build.crm.service.lead;

import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.LeadDepense;

import java.util.List;

public interface LeadDepenseService {
    public List<LeadDepense> findById(int leadId);
    public List<LeadDepense> findByCustomerId(int leadId);
    public LeadDepense findByLeadId(int leadId);
    public double getSumDepensesCustomerLeads(int customerId) ;
    public void save(LeadDepense leadDepense);
}

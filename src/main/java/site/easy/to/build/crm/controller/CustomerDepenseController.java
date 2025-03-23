package site.easy.to.build.crm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.LeadDepense;
import site.easy.to.build.crm.entity.TicketDepense;
import site.easy.to.build.crm.service.contract.ContractService;
import site.easy.to.build.crm.service.customer.CustomerBudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadDepenseService;
import site.easy.to.build.crm.service.ticket.TicketDepenseService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import java.util.List;

@Controller
public class CustomerDepenseController {
    private final AuthenticationUtils authenticationUtils;
    private final CustomerLoginInfoService customerLoginInfoService;
    private final CustomerService customerService;
    private final LeadDepenseService leadDepenseService;
    private final TicketDepenseService ticketDepenseService;
    private final CustomerBudgetService customerBudgetService;


    public CustomerDepenseController(AuthenticationUtils authenticationUtils, CustomerLoginInfoService customerLoginInfoService, CustomerService customerService, LeadDepenseService leadDepenseService, TicketDepenseService ticketDepenseService, CustomerBudgetService customerBudgetService) {
        this.authenticationUtils = authenticationUtils;
        this.customerLoginInfoService = customerLoginInfoService;
        this.customerService = customerService;
        this.leadDepenseService = leadDepenseService;
        this.ticketDepenseService = ticketDepenseService;
        this.customerBudgetService = customerBudgetService;
    }

    @GetMapping("/customer/my-depenses")
    public String showAllDepenses(Model model, Authentication authentication) {
        int customerId = authenticationUtils.getLoggedInUserId(authentication);
        System.out.println(customerId);
        CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findById(customerId);
        Customer customer = customerService.findByEmail(customerLoginInfo.getEmail());

        List<LeadDepense> leadDepenses=leadDepenseService.findByCustomerId(customer.getCustomerId());
        double totalLeadDepenses=leadDepenseService.getSumDepensesCustomerLeads(customer.getCustomerId());
        model.addAttribute("leadDepenses", leadDepenses);
        model.addAttribute("totalLeadDepenses", totalLeadDepenses);

        List<TicketDepense> ticketDepenses=ticketDepenseService.findByCustomerId(customer.getCustomerId());
        double totalTicketDepenses=ticketDepenseService.getSumTicketDepense(customer.getCustomerId());
        model.addAttribute("ticketDepenses",ticketDepenses);
        model.addAttribute("totalTicketDepenses", totalTicketDepenses);

        double total=totalLeadDepenses+totalTicketDepenses;
        model.addAttribute("total", total);

        double budgetGlobal=customerBudgetService.getSum(customer.getCustomerId());
        model.addAttribute("sum",budgetGlobal);
        if (total>=budgetGlobal*0.8) {
            model.addAttribute("alertMessage","Alerte: Votre budget a atteint les 80%");
        }

        return "customer-info/my-depenses";
    }
}

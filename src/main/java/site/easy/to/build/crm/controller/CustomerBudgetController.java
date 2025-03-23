package site.easy.to.build.crm.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.easy.to.build.crm.entity.Contract;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerBudget;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.service.contract.ContractService;
import site.easy.to.build.crm.service.customer.CustomerBudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadDepenseService;
import site.easy.to.build.crm.service.ticket.TicketDepenseService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CustomerBudgetController {
    private final CustomerBudgetService customerBudgetService;
    private final AuthenticationUtils authenticationUtils;
    private final CustomerLoginInfoService customerLoginInfoService;
    private final CustomerService customerService;
    private final ContractService contractService;
    private final LeadDepenseService leadDepenseService;
    private final TicketDepenseService ticketDepenseService;

    public CustomerBudgetController(CustomerBudgetService customerBudgetService, AuthenticationUtils authenticationUtils, CustomerLoginInfoService customerLoginInfoService, ContractService contractService, CustomerService customerService, LeadDepenseService leadDepenseService, TicketDepenseService ticketDepenseService) {
        this.customerBudgetService = customerBudgetService;
        this.authenticationUtils = authenticationUtils;
        this.customerLoginInfoService = customerLoginInfoService;
        this.contractService = contractService;
        this.customerService = customerService;
        this.leadDepenseService = leadDepenseService;
        this.ticketDepenseService = ticketDepenseService;
    }


    @PostMapping("/customer/add-budget")
    public String addBudget(Authentication authentication,
                            @Validated @ModelAttribute("customerBudget")  CustomerBudget customerBudget,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/customer/add-budget";
        }

        int customerId = authenticationUtils.getLoggedInUserId(authentication);
        System.out.println(customerId);
        CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findById(customerId);
        Customer customer = customerService.findByEmail(customerLoginInfo.getEmail());

        customerBudget.setCustomer(customer);
        customerBudget.setDate(LocalDateTime.now());
        System.out.println(customerBudget.getMontant());
        customerBudgetService.save(customerBudget);

        return "redirect:/customer/my-budget";
    }

    @GetMapping("/customer/my-budgets")
    public String showMyBudgets(Model model, Authentication authentication){
        int customerId = authenticationUtils.getLoggedInUserId(authentication);
        CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findById(customerId);
        Customer customer = customerService.findByEmail(customerLoginInfo.getEmail());
        List<CustomerBudget> customerBudgets=customerBudgetService.findByCustomerCustomerId(customer.getCustomerId());
        double sum=customerBudgetService.getSum(customer.getCustomerId());
//        System.out.println(sum);

        model.addAttribute("budgets", customerBudgets);
        model.addAttribute("sum", sum);

        double totalLeadDepenses=leadDepenseService.getSumDepensesCustomerLeads(customer.getCustomerId());
        double totalTicketDepenses=ticketDepenseService.getSumTicketDepense(customer.getCustomerId());
        double total=totalLeadDepenses+totalTicketDepenses;

        model.addAttribute("total", total);

        if (total>=sum*0.8) {
            model.addAttribute("alertMessage","Alerte: Votre budget a atteint les 80%");
        }


        return "customer-info/my-budgets";
    }

    @GetMapping("/customer/add-budget")
    public String insertBudgets(Model model,Authentication authentication){
        int customerId = authenticationUtils.getLoggedInUserId(authentication);
        CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findById(customerId);
        Customer customer = customerService.findByEmail(customerLoginInfo.getEmail());
        List<CustomerBudget> customerBudgets=customerBudgetService.findByCustomerCustomerId(customer.getCustomerId());

        model.addAttribute("budgets", customerBudgets);
        model.addAttribute("customerBudget",new CustomerBudget());
        return "customer-info/insert-budget";
    }
}

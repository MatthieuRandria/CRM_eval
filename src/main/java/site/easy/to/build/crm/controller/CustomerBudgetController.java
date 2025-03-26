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
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.service.contract.ContractService;
import site.easy.to.build.crm.service.customer.CustomerBudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadDepenseService;
import site.easy.to.build.crm.service.ticket.TicketDepenseService;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.service.util.TauxService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CustomerBudgetController {
    private final CustomerBudgetService customerBudgetService;
    private final AuthenticationUtils authenticationUtils;
    private final CustomerLoginInfoService customerLoginInfoService;
    private final CustomerService customerService;
    private final UserService userService;
    private final LeadDepenseService leadDepenseService;
    private final TicketDepenseService ticketDepenseService;
    private final TauxService tauxService;

    public CustomerBudgetController(CustomerBudgetService customerBudgetService, AuthenticationUtils authenticationUtils, CustomerLoginInfoService customerLoginInfoService, CustomerService customerService, UserService userService, LeadDepenseService leadDepenseService, TicketDepenseService ticketDepenseService, TauxService tauxService) {
        this.customerBudgetService = customerBudgetService;
        this.authenticationUtils = authenticationUtils;
        this.customerLoginInfoService = customerLoginInfoService;
        this.customerService = customerService;
        this.userService = userService;
        this.leadDepenseService = leadDepenseService;
        this.ticketDepenseService = ticketDepenseService;
        this.tauxService = tauxService;
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

        // Taux tokony misy any anaty base
        double taux=tauxService.getMostRecentTaux(1).get(0).getTaux();
//        System.out.println("Valeur taux:" + taux);

        if (total>=sum*taux/100) {
            model.addAttribute("alertMessage","Alerte: Votre budget a atteint les "+taux+"%");
        }
        return "customer-info/my-budgets";
    }

    @PostMapping("/manager/add-budget")
    public String insertBudget(Model model,
                               Authentication authentication,
                               @RequestParam("customerId") int idCustomer,
                               @Validated @ModelAttribute("customerBudget") CustomerBudget customerBudget,
                               BindingResult bindingResult) {
        int currentUserId = authenticationUtils.getLoggedInUserId(authentication);
        User loggedInUser = userService.findById(currentUserId);
        if(loggedInUser.isInactiveUser()) {
            return "error/account-inactive";
        }
        if (bindingResult.hasErrors()) {
            return "customer-info/insert-budget";
        }

        Customer customer=customerService.findByCustomerId(idCustomer);
        customerBudget.setCustomer(customer);
        customerBudget.setDate(LocalDateTime.now());
        System.out.println("Budget pour "+customer.getName()+": "+customerBudget.getMontant());

        customerBudgetService.save(customerBudget);
        return "redirect:/manager/add-budget";
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
}

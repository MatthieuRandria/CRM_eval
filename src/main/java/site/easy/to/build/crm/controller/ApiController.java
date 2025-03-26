package site.easy.to.build.crm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.entity.settings.Taux;
import site.easy.to.build.crm.repository.TauxRepository;
import site.easy.to.build.crm.service.customer.CustomerBudgetService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadDepenseService;
import site.easy.to.build.crm.service.ticket.TicketDepenseService;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.service.util.TauxService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ApiController {

    private UserService userService;
    private CustomerBudgetService customerBudgetService;
    private CustomerService customerService;
    private TicketDepenseService ticketDepenseService;
    private LeadDepenseService leadDepenseService;
    private TauxService tauxService;

    public ApiController(UserService userService,CustomerBudgetService customerBudgetService, CustomerService customerService,
                         TicketDepenseService y, LeadDepenseService l, TauxService t) {
        this.customerBudgetService = customerBudgetService;
        this.userService = userService;
        this.customerService = customerService;
        this.ticketDepenseService = y;
        this.leadDepenseService = l;
        this.tauxService = t;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestParam("email")String email){
        User user=userService.findByEmail(email);
        if(user!=null){
            List<Role> roles=user.getRoles();
            for (Role role : roles) {
                if(role.getName().equals("ROLE_MANAGER")){
                    Map<String,Object> map=new HashMap<>();
                    map.put("id",user.getId());
                    map.put("username",user.getUsername());
                    map.put("email",user.getEmail());
                    return ResponseEntity.ok(map);
                }
            }
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/all-budget")
    public ResponseEntity<CustomerBudget[]> getBudgets(){
        List<CustomerBudget> customerBudgets=customerBudgetService.findAll();
        return ResponseEntity.ok(customerBudgets.toArray(new CustomerBudget[]{}));
    }

    @GetMapping("/all-ticket-depense")
    public ResponseEntity<TicketDepense[]> getTicektDepense(){
        List<TicketDepense> ticketDepenses=ticketDepenseService.findAll();
        return ResponseEntity.ok(ticketDepenses.toArray(new TicketDepense[]{}));
    }

    @GetMapping("/all-lead-depense")
    public ResponseEntity<LeadDepense[]> getLeadDepense(){
        List<LeadDepense> ticketDepenses=leadDepenseService.findAll();
        return ResponseEntity.ok(ticketDepenses.toArray(new LeadDepense[]{}));
    }

    @GetMapping("delete/ticket/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable("id")int id){
        ticketDepenseService.delete(ticketDepenseService.findById(id));
        return ResponseEntity.ok("Deleted ticket");
    }

    @GetMapping("delete/lead/{id}")
    public ResponseEntity<String> deleteLead(@PathVariable("id")int id){
        LeadDepense leadDepense=leadDepenseService.findById(id).get(0);
        System.out.println(leadDepense.getId());
        leadDepenseService.delete(leadDepense);
        return ResponseEntity.ok("Deleted lead");
    }

    @GetMapping("/lead-depense/{id}")
    public ResponseEntity<LeadDepense> detailLeadDepense(@PathVariable("id")int id){
        LeadDepense leadDepense=leadDepenseService.findById(id).get(0);
        return ResponseEntity.ok(leadDepense);
    }

    @GetMapping("/ticket-depense/{id}")
    public ResponseEntity<TicketDepense> detailTicketDepense(@PathVariable("id")int id){
        TicketDepense ticketDepense=ticketDepenseService.findById(id);
        return ResponseEntity.ok(ticketDepense);
    }

    @PostMapping("/update/lead-depense/{id}/{montant}")
    public ResponseEntity<String> updateLeadDepense(@PathVariable("id")int id,@PathVariable("montant")double montant){
        leadDepenseService.update(id,montant);
        return ResponseEntity.ok("Lead Depense updated");
    }

    @PostMapping("/update/ticket-depense/{id}/{montant}")
    public ResponseEntity<String> updateTicketDepense(@PathVariable("id")int id,@PathVariable("montant")double montant){
        ticketDepenseService.update(id,montant);
        return ResponseEntity.ok("Lead Depense updated");
    }

    @PostMapping("/taux/create")
    public ResponseEntity<String> setTaux(@RequestParam("taux")double taux){
        Taux taux1=new Taux();
        taux1.setTaux(taux);
        taux1.setCreated_at(LocalDateTime.now());
        tauxService.insert(taux1);
        return ResponseEntity.ok("Taux ajoute!");
    }


}

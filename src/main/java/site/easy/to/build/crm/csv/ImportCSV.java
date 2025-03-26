package site.easy.to.build.crm.csv;

import org.springframework.security.crypto.password.PasswordEncoder;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.customer.CustomerBudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadDepenseService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketDepenseService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportCSV {
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final CustomerLoginInfoService customerLoginInfoService;
    private final TicketService ticketService;
    private final TicketDepenseService ticketDepenseService;
    private final LeadService leadService;
    private final LeadDepenseService leadDepenseService;
    private final CustomerBudgetService budgetCustomerService;
    List<CsvFile> listFiles = new ArrayList<CsvFile>();

    public ImportCSV(PasswordEncoder passwordEncoder, CustomerService customerService, CustomerLoginInfoService customerLoginInfoService, TicketService ticketService, TicketDepenseService ticketDepenseService, LeadService leadService, LeadDepenseService leadDepenseService, CustomerBudgetService budgetCustomerService) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.customerLoginInfoService = customerLoginInfoService;
        this.ticketService = ticketService;
        this.ticketDepenseService = ticketDepenseService;
        this.leadService = leadService;
        this.leadDepenseService = leadDepenseService;
        this.budgetCustomerService = budgetCustomerService;
    }


    public List<CsvFile> getListFiles() {
        return listFiles;
    }

    public void setListFiles(List<CsvFile> listFiles) {
        this.listFiles = listFiles;
    }

    public void addListFile(CsvFile file) {
        this.listFiles.add(file);
    }

    public void importData (User manager, User employee, String separateur) throws Exception {
        CsvUtil csvUtil = new CsvUtil();
        ImportCustomer importCustomer = new ImportCustomer(passwordEncoder);
        importCustomer.setCustomers(customerService.findAll());
        ImportTicket importTicket = new ImportTicket();
        ImportLead importLead = new ImportLead();
        ImportBudget importBudget = new ImportBudget();
        for (int i = 0; i < listFiles.size(); i++) {
            CsvFile file = listFiles.get(i);
            System.out.println("Treating :"+file.name);
            try {
                List<HashMap<String,Object>> data = csvUtil.getDataFromCSV(file.getPath(), separateur, file.getTypes());
                if(i==0){
                    importCustomer.readCustomers(manager,data);
                }
                if (i==1){
                    importLead.readLead(manager,employee,data,importCustomer);
                    importTicket.readTicket(manager,employee,data,importCustomer);
                }
                if (i==2){
                    importBudget.readBudget(data,importCustomer);
                }
            } catch (Exception e) {
                throw new Exception(file.getName()+" "+e.getMessage());
            }
        }

        importCustomer.save(customerService,customerLoginInfoService,importLead,importTicket,importBudget);
        importLead.save(leadService,leadDepenseService);
        importTicket.save(ticketService,ticketDepenseService);
        importBudget.save(budgetCustomerService);

    }
}

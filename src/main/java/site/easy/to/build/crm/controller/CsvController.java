package site.easy.to.build.crm.controller;


import jakarta.servlet.http.Part;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.csv.CsvFile;
import site.easy.to.build.crm.csv.CsvUtil;
import site.easy.to.build.crm.csv.ImportCSV;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.customer.CustomerBudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadDepenseService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketDepenseService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RequestMapping("/csv-manager")
public class CsvController {
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final CustomerLoginInfoService customerLoginInfoService;
    private final TicketService ticketService;
    private final TicketDepenseService ticketDepenseService;
    private final LeadService leadService;
    private final LeadDepenseService leadDepenseService;
    private final AuthenticationUtils authenticationUtils;
    private final UserService userService;
    private final CustomerBudgetService budgetCustomerService;
    private static final String UPLOAD_DIR = "/upload";

    public CsvController(PasswordEncoder passwordEncoder, CustomerService customerService, CustomerLoginInfoService customerLoginInfoService, TicketService ticketService, TicketDepenseService ticketDepenseService, LeadService leadService, LeadDepenseService leadDepenseService, AuthenticationUtils authenticationUtils, UserService userService, CustomerBudgetService budgetCustomerService) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.customerLoginInfoService = customerLoginInfoService;
        this.ticketService = ticketService;
        this.ticketDepenseService = ticketDepenseService;
        this.leadService = leadService;
        this.leadDepenseService = leadDepenseService;
        this.authenticationUtils = authenticationUtils;
        this.userService = userService;
        this.budgetCustomerService = budgetCustomerService;
    }

    @GetMapping("/csv-form")
    public String form() {
        return "data/csv-form";
    }

    @PostMapping("/import")
    public String import_csv(Authentication authentication, Model model, 
        @RequestParam("file") MultipartFile file, @RequestParam("file2") MultipartFile file2,@RequestParam("file3") MultipartFile file3, 
        @RequestParam("separateur") String separateur) {

        CsvFile csvFile1 = new CsvFile();
        csvFile1.getTypes().put("customer_email",String.class);
        csvFile1.getTypes().put("customer_name",String.class);
        csvFile1.setName(file.getOriginalFilename());
        csvFile1.setPath(uploadCsv(file));

        CsvFile csvFile2 = new CsvFile();
        csvFile2.getTypes().put("customer_email",String.class);
        csvFile2.getTypes().put("subject_or_name",String.class);
        csvFile2.getTypes().put("type",String.class);
        csvFile2.getTypes().put("status",String.class);
        csvFile2.getTypes().put("expense",double.class);
        csvFile2.setName(file2.getOriginalFilename());
        csvFile2.setPath(uploadCsv(file2));

        CsvFile csvFile3 = new CsvFile();
        csvFile3.getTypes().put("customer_email",String.class);
        csvFile3.getTypes().put("Budget",double.class);
        csvFile3.setName(file3.getOriginalFilename());
        csvFile3.setPath(uploadCsv(file3));

        ImportCSV importCSV = new ImportCSV(passwordEncoder,customerService,customerLoginInfoService,ticketService,ticketDepenseService,leadService,leadDepenseService,budgetCustomerService);
        importCSV.addListFile(csvFile1);
        importCSV.addListFile(csvFile2);
        importCSV.addListFile(csvFile3);

        int userId = authenticationUtils.getLoggedInUserId(authentication);
        User manager = userService.findById(userId);
        try {
            importCSV.importData(manager,manager,separateur);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "data/csv-form";
        }
        model.addAttribute("alerte", "import success");
        return "data/csv-form";
    }

    public String uploadCsv(MultipartFile file){
        try{
            Path uploadDir = Paths.get(UPLOAD_DIR).toAbsolutePath();
            Files.createDirectories(uploadDir);

            Path filePath = uploadDir.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

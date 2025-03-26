package site.easy.to.build.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.easy.to.build.crm.service.util.DBUtilService;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/database")
public class DBCleanerController {

    private final DBUtilService dbUtilService;
    public DBCleanerController(DBUtilService dbUtilService) {
        this.dbUtilService = dbUtilService;
    }

    @GetMapping("/clear-db")
    public String clearDB() {
        List<String> list = new ArrayList<>();
        list.add("contract_settings");
        list.add("email_template");
        list.add("trigger_contract");
        list.add("trigger_ticket");
        list.add("ticket_settings");
        list.add("trigger_lead");
        list.add("lead_settings");
        list.add("file");
        list.add("google_drive_file");
        list.add("lead_action");
        list.add("customer");
        list.add("customer_login_info");
        list.add("customer_budget");
        list.add("ticket_depense");
        list.add("lead_depense");

        dbUtilService.disableConstraint(0);         // deactive
        for (String table: list){
//            System.out.println(table);
            dbUtilService.clearTable(table);
        }
        dbUtilService.disableConstraint(1);         // active

        return "redirect:/database/settings";

    }


}

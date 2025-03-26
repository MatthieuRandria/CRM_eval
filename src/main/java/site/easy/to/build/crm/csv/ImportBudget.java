package site.easy.to.build.crm.csv;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerBudget;
import site.easy.to.build.crm.service.customer.CustomerBudgetService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportBudget {
    List<CustomerBudget> budgets = new ArrayList<>();

    public ImportBudget() {
    }

    public ImportBudget(List<CustomerBudget> budgets) {
        this.budgets = budgets;
    }

    public List<CustomerBudget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<CustomerBudget> budgets) {
        this.budgets = budgets;
    }

    public List<CustomerBudget> readBudget(List<HashMap<String, Object>> data, ImportCustomer importCustomer) throws Exception {
        List<CustomerBudget> result = new ArrayList<>();
        int count = 1;
        for (HashMap<String, Object> map : data) {
            CustomerBudget budgetCustomer = new CustomerBudget();
            budgetCustomer.setDate(LocalDateTime.now());

            Customer customer = importCustomer.isExistCustomer(map.get("customer_email").toString());
            if (customer != null) {
                budgetCustomer.setCustomer(customer);
            }
            else {
                throw new Exception("lignes "+count+" : Le customer lier n'existe pas");
            }

            double montant = Double.parseDouble(map.get("Budget").toString());
            if (montant >= 0) {
                budgetCustomer.setMontant(Double.valueOf(montant));
            }
            else {
                throw new Exception("lignes "+count+" : Montant invalide");
            }

            budgets.add(budgetCustomer);
            result.add(budgetCustomer);
            count++;
        }
        return result;

    }

    public void save(CustomerBudgetService budgetCustomerService) throws Exception {
        for (CustomerBudget budgetCustomer : budgets) {
            budgetCustomerService.save(budgetCustomer);
        }
    }

    public void updateCustomerForBudget(Customer customer){
        for (CustomerBudget budget : budgets) {
            if (budget.getCustomer().getEmail().equals(customer.getEmail())) {
                budget.setCustomer(customer);
            }
        }
    }
}

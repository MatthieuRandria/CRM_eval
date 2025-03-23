package site.easy.to.build.crm.service.customer;

import org.springframework.data.domain.Pageable;
import site.easy.to.build.crm.entity.CustomerBudget;

import java.util.List;

public interface CustomerBudgetService {
    public List<CustomerBudget> findById(int customerId);

    public List<CustomerBudget> findByCustomerCustomerId(int customerId);

    public List<CustomerBudget> findAll();

    public void save(CustomerBudget customerBudget);

    public double getSum(int customerId);

    public List<CustomerBudget> findByIdOrderByDateDesc(int customerId, Pageable pageable);
}

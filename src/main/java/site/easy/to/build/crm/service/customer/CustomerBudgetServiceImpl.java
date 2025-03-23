package site.easy.to.build.crm.service.customer;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.CustomerBudget;
import site.easy.to.build.crm.repository.CustomerBudgetRepository;

import java.util.List;

@Service
public class CustomerBudgetServiceImpl implements CustomerBudgetService {
    private CustomerBudgetRepository customerBudgetRepository;

    public CustomerBudgetServiceImpl(CustomerBudgetRepository customerBudgetRepository) {
        this.customerBudgetRepository = customerBudgetRepository;
    }


    @Override
    public List<CustomerBudget> findById(int customerId) {
        return customerBudgetRepository.findById(customerId);
    }


    @Override
    public List<CustomerBudget> findAll() {
        return customerBudgetRepository.findAll();
    }

    @Override
    public void save(CustomerBudget customerBudget) {
        customerBudgetRepository.save(customerBudget);
    }

    @Override
    public double getSum(int customerId) {
        double sum = 0;
        List<CustomerBudget>customerBudgets = customerBudgetRepository.findById(customerId);
        if (customerBudgets != null) {
            for (CustomerBudget customerBudget :customerBudgets ) {
                sum+=customerBudget.getMontant();
            }
        }
        return sum;
    }

    @Override
    public List<CustomerBudget> findByIdOrderByDateDesc(int customerId, Pageable pageable) {
        return customerBudgetRepository.findByIdOrderByDateDesc(customerId, pageable);
    }
}

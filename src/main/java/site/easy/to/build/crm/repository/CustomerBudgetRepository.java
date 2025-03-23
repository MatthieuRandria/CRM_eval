package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.CustomerBudget;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CustomerBudgetRepository extends JpaRepository<CustomerBudget, Integer> {
    public List<CustomerBudget> findById(int IdCustomer);

    public List<CustomerBudget> findByCustomerCustomerId(int IdCustomer);

    public List<CustomerBudget> findAll();

    public List<CustomerBudget> findByIdOrderByDateDesc(int customerId, Pageable pageable);

}

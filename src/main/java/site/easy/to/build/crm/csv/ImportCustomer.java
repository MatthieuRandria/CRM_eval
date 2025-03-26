package site.easy.to.build.crm.csv;

import org.springframework.security.crypto.password.PasswordEncoder;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.util.EmailTokenUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportCustomer {
    private final PasswordEncoder passwordEncoder;
    List<Customer> customers = new ArrayList<>();

    public ImportCustomer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> readCustomers(User manager, List<HashMap<String,Object>> data) throws Exception {
        List<Customer> result = new ArrayList<>();
        int count = 1;
        for (HashMap<String,Object> map : data) {
            Customer customer = new Customer();
            customer.setName((String) map.get("customer_name"));
            customer.setEmail((String) map.get("customer_email"));
            customer.setAddress("Itaosy-CITE");
            customer.setCity("Antananarivo");
            customer.setState("Madagascar");
            customer.setCountry("Afrique");
            customer.setDescription("humain");
            customer.setPhone("0344424297");
            customer.setFacebook("facebook.com");
            customer.setTwitter("twitter.com");
            customer.setYoutube("youtube.com");
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUser(manager);

            // Auth-Customer-info
            CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
            String hashPassword = passwordEncoder.encode("1234");
            customerLoginInfo.setPassword(hashPassword);
            customerLoginInfo.setPasswordSet(true);
            customerLoginInfo.setEmail(customer.getEmail());
            customerLoginInfo.setToken(EmailTokenUtils.generateToken());
            customer.setCustomerLoginInfo(customerLoginInfo);

            // Verif customer
            Customer existingCustomer = this.isExistCustomer(customer.getEmail());
            if (existingCustomer == null) {
                result.add(customer);
                customers.add(customer);
            }
            else {
                throw new Exception("lignes "+count+" : Le client existe deja");
            }
            count++;
        }
        return result;
    }

    public Customer isExistCustomer(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    public void save(CustomerService customerService, CustomerLoginInfoService customerLoginInfoService,ImportLead importLead,ImportTicket importTicket,ImportBudget importBudget) throws Exception {
        for (Customer customer : customers) {
            if (customer.getCustomerId()==null){
                Customer newCustomer = new Customer();
                newCustomer.setName(customer.getName());
                newCustomer.setEmail(customer.getEmail());
                newCustomer.setAddress(customer.getAddress());
                newCustomer.setCity(customer.getCity());
                newCustomer.setState(customer.getState());
                newCustomer.setCountry(customer.getCountry());
                newCustomer.setDescription(customer.getDescription());
                newCustomer.setPhone(customer.getPhone());
                newCustomer.setFacebook(customer.getFacebook());
                newCustomer.setTwitter(customer.getTwitter());
                newCustomer.setYoutube(customer.getYoutube());
                newCustomer.setCreatedAt(customer.getCreatedAt());
                newCustomer.setUser(customer.getUser());

                Customer createdCustomer = customerService.save(newCustomer);
                customer.getCustomerLoginInfo().setCustomer(createdCustomer);

                CustomerLoginInfo createdCustomerLoginInfo = customerLoginInfoService.save(customer.getCustomerLoginInfo());
                createdCustomer.setCustomerLoginInfo(createdCustomerLoginInfo);
                customerService.save(createdCustomer);

                importLead.updateCustomerForLead(createdCustomer);
                importTicket.updateCustomerForTicket(createdCustomer);
                importBudget.updateCustomerForBudget(createdCustomer);
            }
        }
    }
}

package site.easy.to.build.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_budget")
public class CustomerBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "montant")
    @NotBlank(message = "Montant is required")
    private double montant;

    @Column(name = "created_at")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;


    public CustomerBudget(Integer id, double montant, LocalDateTime date) {
        this.id = id;
        this.montant = montant;
        this.date = date;
    }

    public CustomerBudget() {
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

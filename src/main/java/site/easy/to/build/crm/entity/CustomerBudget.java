package site.easy.to.build.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_budget")
public class CustomerBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "montant")
    @NotNull(message = "Montant is required")
    @Min(value = 1, message = "Montant must be at least 1")
    private double montant;

    @Column(name = "created_at")
    private LocalDateTime date;

    @Column(name = "nom")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;


    public CustomerBudget(Integer id, double montant, LocalDateTime date,Customer customer) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.customer = customer;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

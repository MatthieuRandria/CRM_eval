package site.easy.to.build.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lead_depense")
public class LeadDepense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "montant")
    private double montant;

    @Column(name = "created_at")
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "id_lead")
//    @JsonIgnore
    private Lead lead;

    public LeadDepense() {
    }

    public LeadDepense(int id, double montant, LocalDateTime date, Lead lead) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.lead = lead;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}

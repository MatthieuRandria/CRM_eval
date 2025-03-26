package site.easy.to.build.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_depense")
public class TicketDepense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "montant")
    private double montant;

    @Column(name = "created_at")
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "id_ticket")
//    @JsonIgnore
    private Ticket ticket;

    @Column(name = "nom")
    private String nom;

    public TicketDepense() {
    }

    public TicketDepense(int id, double montant, LocalDateTime date, Ticket ticket) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}

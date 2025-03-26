package site.easy.to.build.crm.entity.settings;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "taux")
public class Taux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "taux")
    private double taux;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    public Taux() {}
    public Taux(double taux) {
        setTaux(taux);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}

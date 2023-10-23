package BuildWeek1.entities;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "servizio")
public class Servizio {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "data_inizio", nullable = false)
    private LocalDateTime dataInizio;
    @Column(name = "data_fine")
    private LocalDateTime dataFine;

    @ManyToOne
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;

    @Column(name = "durata_servizio")

    private Duration durataServizio;

    public Servizio() {
    }

    public Servizio(LocalDateTime dataInizio, Mezzo mezzo) {
        this.dataInizio = dataInizio;
        this.mezzo = mezzo;
    }

    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    public LocalDateTime getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDateTime dataFine) {
        this.dataFine = dataFine;
        Duration durata = Duration.between(this.dataInizio, this.dataFine);
        this.durataServizio = durata;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public Duration getTempoManutenzione() {
        return durataServizio;
    }


    @Override
    public String toString() {
        return "Servizio{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + mezzo +
                ", durataServizio=" + durataServizio +
                '}';
    }
}

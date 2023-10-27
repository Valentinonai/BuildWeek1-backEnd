package BuildWeek1.entities;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "manutenzione")
public class Manutenzione {
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

    @Column(name = "tempo_manutenzione")

    private long tempoManutenzione;

    public Manutenzione() {
    }

    public Manutenzione(LocalDateTime dataInizio, Mezzo mezzo) {
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
        this.tempoManutenzione = durata.getSeconds()/60;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public long getTempoManutenzione() {
        return tempoManutenzione;
    }


    @Override
    public String toString() {
        return "Manutenzione{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + mezzo +
                ", tempoManutenzione=" + tempoManutenzione +
                '}';
    }
}

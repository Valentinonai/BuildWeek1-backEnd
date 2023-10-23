package BuildWeek1.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tessera")
public class Tessera {
    @Id
    @Column(name = "codice_tessera")
    private long codiceTessera;
    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;
    private LocalDate dataScadenza;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userTessera;

    public Tessera() {
    }

    public Tessera( LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusDays(365);
    }

    public long getCodiceTessera() {
        return codiceTessera;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusDays(365);
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "codiceTessera='" + codiceTessera + '\'' +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", userTessera=" + userTessera +
                '}';
    }
}

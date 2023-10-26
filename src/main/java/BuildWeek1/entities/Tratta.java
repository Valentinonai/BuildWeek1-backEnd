package BuildWeek1.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "tratte")
public class Tratta {
    @Id
    @GeneratedValue
    private long id;
    private String zonaPartenza;
    private String capolinea;
    private long tempoStimato;
    private long tempoEffettivo;
    @ElementCollection
    @Column(name = "mezzo_per_tratta")
    private Map<Mezzo,Integer> trattaPerMezzo;

    @ManyToMany
    @JoinTable(
            name = "tratte_mezzi",
            joinColumns = @JoinColumn(name = "tratta_id"),
            inverseJoinColumns = @JoinColumn(name = "mezzi_id")
    )
    private Set<Mezzo> mezzo;

    @ManyToMany
    @JoinTable(
            name = "tratte_tickets",
            joinColumns = @JoinColumn(name = "tratta_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private Set<Ticket> tickets;


    public Tratta() {
    }

    public Tratta(long id, String zonaPartenza, String capolinea, long tempoStimato, long tempoEffettivo) {
        this.id = id;
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoStimato = tempoStimato;
        this.tempoEffettivo = tempoEffettivo;
    }

    public Map<Mezzo, Integer> getTrattaPerMezzo() {
        return trattaPerMezzo;
    }

    public void setTrattaPerMezzo(int trattaPerMezzo,Mezzo m) {
        this.trattaPerMezzo.put(m,trattaPerMezzo);
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public long getTempoStimato() {
        return tempoStimato;
    }

    public void setTempoStimato(long tempoStimato) {
        this.tempoStimato = tempoStimato;
    }

    public long getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(long tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoStimato=" + tempoStimato +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }
}

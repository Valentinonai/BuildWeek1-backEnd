package BuildWeek1.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "mezzi")
public class Mezzo {
    @Id
    @GeneratedValue
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mezzo", nullable = false)
    private TipoMezzo tipoMezzo;
    @Column(name = "numero_posti")

    private long numeroPosti;
    @Column(name = "in_manutenzione")
    private boolean inManutenzione;
    @Column(name = "tempo_manutenzione")

    private long tempoManutenzione;
    @Column(name = "in_servizio")
    private boolean inServizio;
    @ManyToMany
    @JoinTable(name = "user_mezzi", joinColumns = @JoinColumn(name = "mezzo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> user;


    private List<LocalDate> dataInizioManutenzione;
    @ManyToMany
    @JoinTable(name = "tickets_mezzi",
            joinColumns = @JoinColumn(name = "mezzo_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private Set<Ticket> tickets;


    @ManyToMany
    @JoinTable(name = "mezzi_tratte",
            joinColumns = @JoinColumn(name = "mezzo_id"),
            inverseJoinColumns = @JoinColumn(name = "tratta_id"))
    private Set<Tratta> tratta;


    private List<LocalDate> dataFineManutenzione;

    private List<LocalDate> dataInizioServizio;

    private List<LocalDate> dataFineServizio;

    public Mezzo() {
    }

    public Mezzo(long id, boolean inManutenzione, long tempoManutenzione, boolean inServizio, List<LocalDate> dataInizioManutenzione, List<LocalDate> dataFineManutenzione, List<LocalDate> dataInizioServizio, List<LocalDate> dataFineServizio) {
        this.id = id;
        this.inManutenzione = inManutenzione;
        this.tempoManutenzione = tempoManutenzione;
        this.inServizio = inServizio;
        this.dataInizioManutenzione = dataInizioManutenzione;
        this.dataFineManutenzione = dataFineManutenzione;
        this.dataInizioServizio = dataInizioServizio;
        this.dataFineServizio = dataFineServizio;
    }

    public long getId() {
        return id;
    }

    public boolean isInManutenzione() {
        return inManutenzione;
    }

    public void setInManutenzione(boolean inManutenzione) {
        this.inManutenzione = inManutenzione;
    }

    public long getTempoManutenzione() {
        return tempoManutenzione;
    }

    public void setTempoManutenzione(long tempoManutenzione) {
        this.tempoManutenzione = tempoManutenzione;
    }

    public boolean isInServizio() {
        return inServizio;
    }

    public void setInServizio(boolean inServizio) {
        this.inServizio = inServizio;
    }

    public List<LocalDate> getDataInizioManutenzione() {
        return dataInizioManutenzione;
    }

    public void setDataInizioManutenzione(LocalDate dataInizioManutenzione) {
        this.dataInizioManutenzione.add(dataInizioManutenzione);
    }

    public List<LocalDate> getDataFineManutenzione() {
        return dataFineManutenzione;
    }

    public void setDataFineManutenzione(LocalDate dataFineManutenzione) {
        this.dataFineManutenzione.add(dataFineManutenzione);
    }

    public List<LocalDate> getDataInizioServizio() {
        return dataInizioServizio;
    }

    public void setDataInizioServizio(LocalDate dataInizioServizio) {
        this.dataInizioServizio.add(dataInizioServizio);
    }

    public List<LocalDate> getDataFineServizio() {
        return dataFineServizio;
    }

    public void setDataFineServizio(LocalDate dataFineServizio) {
        this.dataFineServizio.add(dataFineServizio);
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Tratta> getTratta() {
        return tratta;
    }

    public void setTratta(Set<Tratta> tratta) {
        this.tratta = tratta;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipoMezzo=" + tipoMezzo +
                ", numeroPosti=" + numeroPosti +
                ", inManutenzione=" + inManutenzione +
                ", tempoManutenzione=" + tempoManutenzione +
                ", inServizio=" + inServizio +
                ", user=" + user +
                ", dataInizioManutenzione=" + dataInizioManutenzione +
                ", tickets=" + tickets +
                ", tratta=" + tratta +
                ", dataFineManutenzione=" + dataFineManutenzione +
                ", dataInizioServizio=" + dataInizioServizio +
                ", dataFineServizio=" + dataFineServizio +
                '}';
    }
}

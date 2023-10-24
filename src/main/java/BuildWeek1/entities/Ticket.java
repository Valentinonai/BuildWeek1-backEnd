package BuildWeek1.entities;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;
    @Column(name = "data_validazione")
    private LocalDateTime dataValidazione;
    @Column(name = "data_scadenza")
    private LocalDateTime dataScadenza;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TicketType tipo;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "venditore_id", nullable = false)
    private VenditaBiglietto venditabiglietto;
    @ManyToMany
    @JoinTable(name = "tickets_mezzi",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "mezzo_id"))
    private Set<Mezzo> mezzi;


    public Ticket() {
    }


    public Ticket(LocalDate dataEmissione, TicketType tipo, User user, VenditaBiglietto venditabiglietto) {
        this.dataEmissione = dataEmissione;
        this.tipo = tipo;
        this.user = user;
        this.venditabiglietto = venditabiglietto;
    }

    public long getId() {
        return id;
    }


    public LocalDate getDataEmissione() {
        return dataEmissione;
    }


    public LocalDateTime getDataValidazione() {
        return dataValidazione;
    }

    public void setDataValidazione(LocalDateTime dataValidazione) {
        this.dataValidazione = dataValidazione;
        switch (tipo) {
            case SINGLERIDE -> this.dataScadenza = getDataValidazione().plus(Duration.ofMinutes(90));

            case WEEKLY -> this.dataScadenza = getDataValidazione().plusDays(7);
            case MONTHLY -> this.dataScadenza = getDataValidazione().plusDays(30);


        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VenditaBiglietto getVenditabiglietto() {
        return venditabiglietto;
    }

    public void setVenditabiglietto(VenditaBiglietto venditabiglietto) {
        this.venditabiglietto = venditabiglietto;
    }

    public LocalDateTime getDataScadenza() {
        return dataScadenza;
    }

    public TicketType getTipo() {
        return tipo;
    }

    public Set<Mezzo> getMezzi() {
        return mezzi;
    }

    public void setMezzi(Set<Mezzo> mezzi) {
        this.mezzi = mezzi;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", dataValidazione=" + dataValidazione +
                ", ticketType="+tipo+
                ", venditabiglietto=" + venditabiglietto +
                ", mezzi=" + mezzi +
                '}';
    }
}

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

    public Ticket(LocalDate dataEmissione, User user, VenditaBiglietto venditabiglietto, TicketType tipo) {
        if (dataEmissione != null && !dataEmissione.isAfter(LocalDate.now()) && user != null
                && venditabiglietto != null && tipo != null) {
            this.dataEmissione = dataEmissione;
            this.user = user;
            this.tipo = tipo;
        }
        throw new IllegalArgumentException("la data di emissione del titolo di trasporto non può essere nulla nè oltre la data di oggi" +
                " inoltre deve essere collegata ad un'user e un punto vendita esistente ");
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

    public void setDataValidazione(LocalDate dataValidazione) {
        switch (tipo) {
            case SINGLERIDE -> this.dataScadenza = getDataValidazione().plus(Duration.ofMinutes(90));

            case WEEKLY -> this.dataScadenza = getDataValidazione().plusDays(7);
            case MONTHLY -> this.dataScadenza = getDataValidazione().plusDays(30);
            case YEARLY -> this.dataScadenza = getDataValidazione().plusDays(365);

        }
    }

    public User getUser() {
        return user;
    }

    public VenditaBiglietto getVenditabiglietto() {
        return venditabiglietto;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", dataValidazione=" + dataValidazione +
                ", user=" + user +
                ", venditabiglietto=" + venditabiglietto +
                '}';
    }
}

package BuildWeek1.entities;

import javax.persistence.*;

@Entity
@Table(name = "vendita_biglietti")
public class VenditaBiglietto {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "in_servizio")
    private boolean is_working;
    @Column(name = "tipo_vendita", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoVendita tipoVendita;
    @OneToMany(mappedBy = "venditabiglietto")
    private List<Ticket> ticketList;

    public VenditaBiglietto() {
    }

    public VenditaBiglietto(boolean is_working, TipoVendita tipoVendita) {
        this.is_working = is_working;
        this.tipoVendita = tipoVendita;
    }

    public long getId() {
        return id;
    }


    public boolean isIs_working() {
        return is_working;
    }

    public void setIs_working(boolean is_working) {
        this.is_working = is_working;
    }

    public TipoVendita getTipoVendita() {
        return tipoVendita;
    }

    public void setTipoVendita(TipoVendita tipoVendita) {
        this.tipoVendita = tipoVendita;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public String toString() {
        return "VenditaBiglietto{" +
                "id=" + id +
                ", is_working=" + is_working +
                ", tipoVendita=" + tipoVendita +
                ", ticketList=" + ticketList +
                '}';
    }
}

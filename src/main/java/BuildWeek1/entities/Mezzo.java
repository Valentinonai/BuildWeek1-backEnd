package BuildWeek1.entities;


import BuildWeek1.Dao.ManutenzioneDao;
import BuildWeek1.Dao.MezzoDao;
import BuildWeek1.Dao.ServizioDao;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Column(name = "in_servizio")
    private boolean inServizio;
    @ManyToMany
    @JoinTable(name = "user_mezzi", joinColumns = @JoinColumn(name = "mezzo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> user;

    @OneToMany(mappedBy = "mezzo")
    private List<Manutenzione> manutenzione;
    @OneToMany(mappedBy = "mezzo")
    private List<Servizio> servizi;
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


    public Mezzo() {
    }

    public Mezzo(TipoMezzo tipoMezzo, int numeroPosti) {
     this.tipoMezzo=tipoMezzo;
     this.numeroPosti=numeroPosti;

    }

    public List<Manutenzione> getManutenzione() {
        return manutenzione;
    }

    public void setManutenzione(List<Manutenzione> manutenzione) {
        this.manutenzione = manutenzione;
    }

    public List<Servizio> getServizi() {
        return servizi;
    }

    public void setServizi(List<Servizio> servizi) {
        this.servizi = servizi;
    }

    public long getId() {
        return id;
    }

    public boolean isInManutenzione() {
        return inManutenzione;
    }

    public void setInManutenzione(boolean inManutenzione,EntityManager em,int rnd) {
        this.inManutenzione = inManutenzione;
        ManutenzioneDao sd=new ManutenzioneDao(em);
        if(inManutenzione){
            sd.save(new Manutenzione(LocalDateTime.now(),this));
            System.out.println("Mezzo in manutenzione");
        }else {
             Manutenzione manutenzione=sd.getManutenzioneNull();
            manutenzione.setDataFine(manutenzione.getDataInizio().plusDays(rnd));
            sd.save(manutenzione);
            System.out.println("Mezzo non più in manutenzione");

        }
    }


    public boolean isInServizio() {
        return inServizio;

    }

    public void setInServizio(boolean inServizio,EntityManager em,int rnd) {
        this.inServizio = inServizio;
        ServizioDao sd=new ServizioDao(em);
        if(inServizio){
            sd.save(new Servizio(LocalDateTime.now(),this));
            System.out.println("Mezzo in servizio");
        }else {
            Servizio servizio=sd.getServiceNull();
            servizio.setDataFine(servizio.getDataInizio().plusDays(rnd));
            sd.save(servizio);
            System.out.println("Mezzo non più in servizio");
        }
    }

    public long getNumeroPosti() {
        return numeroPosti;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(User user) throws Exception {
       if(this.user.add(user)) System.out.println("Utente aggiunto");
       else throw new Exception("Utente già sul mezzo");
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

    public void setTratta(Tratta tratta) {
        this.tratta.add(tratta);
    }

    public void eliminaUtente(User user){
        this.user.remove(user);
        System.out.println("Utente scaricato");
    }
    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipoMezzo=" + tipoMezzo +
                ", numeroPosti=" + numeroPosti +
                ", inManutenzione=" + inManutenzione +
                ", inServizio=" + inServizio +
                ", tickets=" + tickets +
                ", tratta=" + tratta +
                '}';
    }
}

package BuildWeek1.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedQuery(name = "findByEmailAndPassword", query = "SELECT u FROM User u WHERE u.email=:email AND u.password=:password")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "userTessera")
    private Tessera tessera;

    @Column(name = "admin")
    private Boolean isAdmin;
    @OneToMany(mappedBy = "user")
    @Column(name = "ticketList")
    private List<Ticket> ticketList;

    @ManyToMany
    @JoinTable(name = "users_mezzi", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "mezzo_id"))
    private Set<Mezzo> mezzo;

    public User() {
    }

    public User(String email, String password, Boolean admin) {
        this.email = email;
        this.password = password;
        this.isAdmin = admin;
    }

    public long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Set<Mezzo> getMezzo() {
        return mezzo;
    }

    public void setMezzo(Set<Mezzo> mezzo) {
        this.mezzo = mezzo;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tessera=" + tessera +
                ", isAdmin=" + isAdmin +
                ", ticketList=" + ticketList +
                ", mezzo=" + mezzo +
                '}';
    }
}

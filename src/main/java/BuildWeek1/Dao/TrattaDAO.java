package BuildWeek1.Dao;

import BuildWeek1.entities.Mezzo;
import BuildWeek1.entities.Tratta;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TrattaDAO {
    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta t) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(t);
        transaction.commit();
        System.out.println("Tratta salvata correttamente");
    }

    public Tratta getById(long id) throws Exception {
        Tratta trattaT = em.find(Tratta.class, id);
        if (trattaT != null) {
            return trattaT;
        } else {
            throw new Exception("Numero tratta non esistente");
        }
    }

    public void findAndDelete(long id) {
        Tratta found = em.find(Tratta.class, id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Elemento eliminato correttamente!");
        } else {
            System.out.println("la tratta con l'Id: " + id + "non è stata trovata");
        }
    }

    public long countTratteByMezzo(long mezzoId, long trattaId) throws Exception {

        Tratta t = getById(trattaId);
        MezzoDao md = new MezzoDao(em);
        Mezzo m = md.getById(mezzoId);
        return t.getTrattaPerMezzo().get(m);
    }
}

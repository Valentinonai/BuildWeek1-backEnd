package BuildWeek1.Dao;

import BuildWeek1.entities.Mezzo;
import BuildWeek1.entities.Ticket;
import BuildWeek1.entities.Tratta;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

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
      Tratta t= em.find(Tratta.class, id);
      if(t!=null)   return t;
      else throw new Exception("Tratta inesistente");
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

      Tratta t= getById(trattaId);
      MezzoDao md=new MezzoDao(em);
      Mezzo m=md.getById(mezzoId);
        Integer x = t.getTrattaPerMezzo().get(m);
      if(x!=null) return x;
      else throw new Exception("Questo mezzo non percorre questa tratta");
    }
}

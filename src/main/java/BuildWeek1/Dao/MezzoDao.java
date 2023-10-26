package BuildWeek1.Dao;

import BuildWeek1.entities.Mezzo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;


public class MezzoDao {


        private static EntityManager em;

        public MezzoDao(EntityManager em) {
            this.em = em;
        }


        public static void save(Mezzo m) {
            try {
                EntityTransaction t = em.getTransaction();
                t.begin();
                em.persist(m);
                t.commit();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public static Mezzo getById(long id) {
            return em.find(Mezzo.class, id);
        }

        public void delete(long id) {
            try {
                EntityTransaction t = em.getTransaction();
                t.begin();
               Mezzo found = em.find(Mezzo.class, id);
                if (found != null) {
                    em.remove(found);
                    t.commit();
                    System.out.println("Elemento eliminato");
                } else System.out.println("Elemento non trovato");


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    public static Mezzo trovaMezzoPerId(int mezzoId) {

        return em.find(Mezzo.class, mezzoId);
    }

    public void cambiaStatoServizio(int mezzoId, boolean nuovoStatoServizio) {
        Mezzo mezzo = MezzoDao.getById(mezzoId);

public List<Mezzo> findMezziDisp(){
    TypedQuery<Mezzo> q = em.createQuery("SELECT m FROM Mezzo m WHERE m.inManutenzione=false", Mezzo.class);
    return q.getResultList();
}
}

    public void cambiaStatoManutenzione(int mezzoId, boolean nuovoStatoManutenzione) {
        Mezzo mezzo = MezzoDao.getById(mezzoId);

        if (mezzo == null) {
            System.err.println("Mezzo non trovato.");
        } else {

            mezzo.setInServizio(nuovoStatoManutenzione);


            MezzoDao.save(mezzo);


            if (nuovoStatoManutenzione) {
                System.out.println("Il veicolo è stato messo in manutenzione.");
            } else {
                System.out.println("Il veicolo è stato messo fuori dalla manutenzione.");
            }
        }
    }

    public int findMezziDisponibili() {

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mezzo> root = cq.from(Mezzo.class);


            Predicate disponibileCondition = cb.and(
                    cb.isFalse(root.get("inManutenzione")),
                    cb.isFalse(root.get("inServizio"))
            );

            cq.select(cb.count(root));
            cq.where(disponibileCondition);

            TypedQuery<Long> query = em.createQuery(cq);
            Long result = query.getSingleResult();

            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }

        }
   }








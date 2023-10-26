package BuildWeek1.Dao;

import BuildWeek1.entities.Manutenzione;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ManutenzioneDao {
  

        private EntityManager em;

        public ManutenzioneDao(EntityManager em) {
            this.em = em;
        }


        public void save(Manutenzione m) {
            try {
                EntityTransaction t = em.getTransaction();
                t.begin();
                em.persist(m);
                t.commit();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public Manutenzione getById(long id) throws Exception {
            Manutenzione m = em.find(Manutenzione.class, id);
            if (m == null) throw new Exception("La manutenzione inserita non esiste");
            else return m;
        }

        public void delete(long id) {
            try {
                EntityTransaction t = em.getTransaction();
                t.begin();
                Manutenzione found = em.find(Manutenzione.class, id);
                if (found != null) {
                    em.remove(found);
                    t.commit();
                    System.out.println("Elemento eliminato");
                } else System.out.println("Elemento non trovato");


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public Manutenzione getManutenzioneNull() {
            TypedQuery<Manutenzione> q = em.createQuery("SELECT m FROM Manutenzione m WHERE m.dataFine=null", Manutenzione.class);
            return q.getSingleResult();

        }
    }


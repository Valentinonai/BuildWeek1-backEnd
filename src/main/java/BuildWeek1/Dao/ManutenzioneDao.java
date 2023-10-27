package BuildWeek1.Dao;

import BuildWeek1.entities.Manutenzione;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
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
    public long tempoTotale(long mezzo_id) throws Exception {
            long x = 0;
            try
            {
                TypedQuery<Long> q=em.createQuery("SELECT SUM(m.tempoManutenzione) FROM Manutenzione m WHERE m.mezzo.id=:mezzo_id", Long.class);
                q.setParameter("mezzo_id",mezzo_id);
              x= q.getSingleResult();
            }catch (Exception e){
                throw new Exception("Il mezzo selezionato non è mai stato in servizio o in manutenzione");
            }

        return x;
    }
    }


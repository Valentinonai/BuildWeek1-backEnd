package BuildWeek1.Dao;

import BuildWeek1.entities.Mezzo;
import BuildWeek1.entities.Tessera;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MezzoDao {


        private EntityManager em;

        public MezzoDao(EntityManager em) {
            this.em = em;
        }


        public void save(Mezzo m) {
            try {
                EntityTransaction t = em.getTransaction();
                t.begin();
                em.persist(m);
                t.commit();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public Mezzo getById(long id) {
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


    }





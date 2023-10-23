package BuildWeek1.Dao;

import BuildWeek1.entities.Tessera;
import BuildWeek1.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class TesseraDao {

        private EntityManager em;

        public TesseraDao(EntityManager em) {
            this.em = em;
        }


        public void save(Tessera tessera) {
            try {
                EntityTransaction t = em.getTransaction();
                t.begin();
                em.persist(tessera);
                t.commit();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public Tessera getById(long id) {
            return em.find(Tessera.class, id);
        }

        public void delete(long id) {
            try {
                EntityTransaction t = em.getTransaction();
                t.begin();
              Tessera found = em.find(Tessera.class, id);
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



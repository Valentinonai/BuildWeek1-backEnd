package BuildWeek1.Dao;

import BuildWeek1.entities.Mezzo;
import BuildWeek1.entities.Tessera;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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








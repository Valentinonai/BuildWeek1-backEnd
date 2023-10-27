package BuildWeek1.Dao;

import BuildWeek1.entities.Mezzo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;


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

        public Mezzo getById(long id) throws Exception {
            Mezzo m=em.find(Mezzo.class, id);
            if(m==null) throw new Exception("Il mezzo inserito non esiste");
            else return m;
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
    public int getMezzoPieno(long mezzo) {
       TypedQuery<Integer> q=em.createQuery("SELECT size(m.user) FROM Mezzo m WHERE m.id=:mezzo",Integer.class);
       q.setParameter("mezzo", mezzo);
        return q.getSingleResult();
    }


public List<Mezzo> findMezziDisp(){
    TypedQuery<Mezzo> q = em.createQuery("SELECT m FROM Mezzo m WHERE m.inManutenzione=false", Mezzo.class);
    return q.getResultList();
}
}





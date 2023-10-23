package BuildWeek1.Dao;

import BuildWeek1.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class UserDao {
    private EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }


    public void save(User u) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(u);
            t.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User getById(long id) {
        return em.find(User.class, id);
    }

    public void delete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            User found = em.find(User.class, id);
            if (found != null) {
                em.remove(found);
                t.commit();
                System.out.println("Elemento eliminato");
            } else System.out.println("Elemento non trovato");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User findByUserAndEmail(String email, String password) {
        TypedQuery<User> q = em.createNamedQuery("findByEmailAndPassword", User.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        return q.getSingleResult();
    }
}

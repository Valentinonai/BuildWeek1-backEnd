package BuildWeek1.Dao;

import BuildWeek1.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDao {
    private EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }


    public void save(User u) throws Exception {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(u);
            t.commit();

        }catch (RollbackException e){
           throw new Exception("Email già utilizzata scegline un'altra");
        }
        catch (Exception e) {
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

    public User findByUserAndEmail(String email, String password) throws Exception {
        TypedQuery<User> q = em.createNamedQuery("findByEmailAndPassword", User.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        User u=q.getSingleResult();
        if(u==null)
            throw new Exception("Accesso negato");
        else
        return u;
    }

    public List<String> getAllUsers(){
        TypedQuery<String> q=em.createNamedQuery("getAllUsers",String.class);
        return q.getResultList();
    }
}

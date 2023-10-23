package BuildWeek1;

import BuildWeek1.Dao.TrattaDAO;
import BuildWeek1.entities.Tratta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerFactory");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        TrattaDAO td =  new TrattaDAO(em);

        Tratta tratta1 =  new Tratta(1,"Milano", "Bergamo", 35,37);
        Tratta tratta2 =  new Tratta(2,"Milano", "Novara", 30,30);

       // td.save(tratta1);
       //td.findAndDelete(tratta2);
    }
}

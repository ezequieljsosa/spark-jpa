package dds;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class InitData {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        EntityManager em = entityManagerFactory.createEntityManager();

        Auto auto1 = new Auto("HGG123", "Astra", "Chevrolet", 2005);
        Auto auto2 = new Auto("TAA123", "Palio", "Fiat", 2011);
        Auto auto3 = new Auto("GDC123", "Siena", "Fiat", 2016);
        Multa m1 = new Multa("undia","Velocidad");
        Multa m2 = new Multa("otrodia","Velocidad");
        auto1.add(m1);
        auto1.add(m2);
        em.getTransaction().begin();
        em.persist(auto1);
        em.persist(auto2);
        em.persist(auto3);
        em.getTransaction().commit();




    }
}

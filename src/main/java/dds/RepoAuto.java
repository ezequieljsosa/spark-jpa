package dds;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepoAuto {

    private EntityManager entityManager;

    public RepoAuto(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Auto> all() {
        //        https://www.arquitecturajava.com/jpa-criteria-api-un-enfoque-diferente/
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Auto> consulta = cb.createQuery(Auto.class);
        Root<Auto> autos = consulta.from(Auto.class);
        return this.entityManager.createQuery(consulta.select(autos)).getResultList();
    }

    public Auto byPatente(String patente) throws AutoNotFoundException {
        if (this.existsPatente(patente)) {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Auto> consulta = cb.createQuery(Auto.class);
            Root<Auto> autos = consulta.from(Auto.class);
            Predicate condicion = cb.equal(autos.get("patente"), patente);
            CriteriaQuery<Auto> where = consulta.select(autos).where(condicion);
            return this.entityManager.createQuery(where).getSingleResult();
        }
        throw new AutoNotFoundException();
    }

    public boolean existsPatente(String patente) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<Auto> autos = consulta.from(Auto.class);
        Predicate condicion = cb.equal(autos.get("patente"), patente);
        CriteriaQuery<Long> select = consulta.where(condicion).select(cb.count(autos));
        return this.entityManager.createQuery(select).getSingleResult() > 0;
    }


    public List<Auto> byMarca(String marca) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Auto> consulta = cb.createQuery(Auto.class);
        Root<Auto> autos = consulta.from(Auto.class);
        Predicate condicion = cb.equal(autos.get("marca"), marca);
        CriteriaQuery<Auto> where = consulta.select(autos).where(condicion);
        return this.entityManager.createQuery(where).getResultList();
    }


    public void borrarAuto(String patente) throws AutoNotFoundException {
        this.entityManager.remove(this.byPatente(patente));
    }


    public void persist(Auto auto) {

        this.entityManager.persist(auto);

    }
}

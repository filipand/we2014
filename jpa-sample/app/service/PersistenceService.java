package service;

import model.BaseEntity;
import model.Student;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Persistence service
 */
public enum PersistenceService {

    INSTANCE;


    /**
     * Merge the given entity. If there is not entity with the given ID yet, create a new one. Otherwise update the
     * existin gone
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends BaseEntity> T merge(T entity) {
        return em().merge(entity);
    }


    /**
     * Persist the given entity. Throws an error in case the entity already exists
     * @param entity
     */
    public void persist(BaseEntity entity) {
        em().persist(entity);
    }


    /**
     * Search for the entity of the given type with the given id
     * @param id The ID of the entity
     * @param entityClazz The class of the entity
     * @param <T>
     * @return
     */
    public <T extends BaseEntity> T findEntity(Long id, Class<T> entityClazz) {
        return em().find(entityClazz, id);
    }


    /**
     * Get a llist of all entities of a certain type
     * @param entityClazz
     * @param <E>
     * @return
     */
    public <E extends BaseEntity> List<E> findEntity(Class<E> entityClazz) {
        Criteria c = ((Session) JPA.em().getDelegate()).createCriteria(entityClazz);
        return c.list();
    }


    /**
     * Get a student based on his unique registration number
     * @param registrationNumber
     * @return
     */
    public Student getStudent(String registrationNumber) {
        Criteria c = ((Session) JPA.em().getDelegate()).createCriteria(Student.class);
        c.add(Restrictions.eq("registrationNumber", registrationNumber));
        return (Student)c.uniqueResult();
    }


    /**
     * Return the entity manager
     * @return
     */
    private EntityManager em() {
        return JPA.em();
    }

}
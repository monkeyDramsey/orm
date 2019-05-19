package swt6.orm.dao;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class GenericDAO implements IDAO {
    @Override
    public void insertEntity(Object entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try{
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e){
            if(tx != null && tx.isActive())
                tx.rollback();
        } finally {
            if(em != null) em.close();
        }
        emFactory.close();
    }

    @Override
    public Object updateEntity(Object entity) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try{
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            entity = em.merge(entity);
            tx.commit();
        } catch (Exception e){
            if(tx != null && tx.isActive())
                tx.rollback();
        } finally {
            if(em != null) em.close();
        }
        emFactory.close();
        return entity;
    }

}

package swt6.orm.dao;

import swt6.orm.domain.Backlog;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BacklogDAO extends GenericDAO {
    @Override
    public void deleteEntity(long id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Backlog userStory = em.find(Backlog.class, id);
            em.remove(userStory);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive())
                tx.rollback();
        } finally {
            if (em != null) em.close();
        }
        emFactory.close();
    }
}

package swt6.orm.dao;

import swt6.orm.domain.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TaskDAO extends GenericDAO {
    @Override
    public void deleteEntity(long id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Task userStory = em.find(Task.class, id);
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

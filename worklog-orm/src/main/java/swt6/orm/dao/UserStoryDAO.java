package swt6.orm.dao;

import swt6.orm.domain.UserStory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserStoryDAO extends GenericDAO {

    @Override
    public void deleteEntity(long id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            UserStory userStory = em.find(UserStory.class, id);
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

package swt6.orm.dao;

import swt6.orm.domain.Sprint;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SprintDAO extends GenericDAO {
    @Override
    public void deleteEntity(long id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Sprint userStory = em.find(Sprint.class, id);
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

    public Sprint findById(long issueId){
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager(); //erzeugt auto transaction
            Sprint sprint = em.find(Sprint.class, issueId);
            JpaUtil.commit();
            return sprint;
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }

    }
}

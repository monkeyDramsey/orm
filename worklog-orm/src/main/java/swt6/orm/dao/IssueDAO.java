package swt6.orm.dao;

import swt6.orm.domain.Issue;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class IssueDAO extends GenericDAO {
    @Override
    public void deleteEntity(long id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Issue issue = em.find(Issue.class, id);
            em.remove(issue);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive())
                tx.rollback();
        } finally {
            if (em != null) em.close();
        }
        emFactory.close();
    }

    public List<Issue> getIssues(){
        try{
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<Issue> qry = em.createQuery(
                    "select i from Issue i",
                    Issue.class
            );
            List<Issue> issueList = qry.getResultList();
            JpaUtil.commit();
            return issueList;
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    public Issue findById(long issueId){
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager(); //erzeugt auto transaction
            Issue issue = em.find(Issue.class, issueId);
            JpaUtil.commit();
            return issue;
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }

    }
}

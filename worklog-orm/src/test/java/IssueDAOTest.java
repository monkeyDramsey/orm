import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.IssueDAO;
import swt6.orm.domain.Issue;

import java.time.LocalDateTime;

public class IssueDAOTest {

    @Test
    public void insertTest(){
        Issue issue = new Issue("no title", "no description", 3L, "done", LocalDateTime.now(), LocalDateTime.now().plusHours(5), "high");
        IssueDAO issueDAO = new IssueDAO();
        issueDAO.insertEntity(issue);
        Assert.assertNotNull(issue);
    }

    @Test
    public void updateTest(){
        Issue issue = new Issue("no title", "no description", 3L, "done", LocalDateTime.now(), LocalDateTime.now().plusHours(5), "high");
        IssueDAO issueDAO = new IssueDAO();
        issue.setFoundDate(LocalDateTime.now().plusHours(5));
        issueDAO.updateEntity(issue);
        Assert.assertEquals(issue.getFixedDate(), issue.getFoundDate());
    }

    @Test
    public void deleteTest(){
        Issue issue = new Issue("no title", "no description", 3L, "done", LocalDateTime.now(), LocalDateTime.now().plusHours(5), "high");
        IssueDAO issueDAO = new IssueDAO();
        issueDAO.insertEntity(issue);
        issueDAO.deleteEntity(issue.getId());
        Assert.assertNull(issue);
    }
}

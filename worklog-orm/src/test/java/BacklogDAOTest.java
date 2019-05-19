import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.BacklogDAO;
import swt6.orm.domain.Backlog;

public class BacklogDAOTest {

    @Test
    public void insertTest(){
        Backlog backlog = new Backlog("log", "show logfile");
        BacklogDAO backlogDAO = new BacklogDAO();
        backlogDAO.insertEntity(backlog);
        Assert.assertNotNull(backlog);
    }

    @Test
    public void updateTest(){
        Backlog backlog = new Backlog("log", "show logfile");
        BacklogDAO backlogDAO = new BacklogDAO();
        backlog.setDescription("new logfile");
        backlogDAO.updateEntity(backlog);
        Assert.assertNotEquals(backlog.getDescription(), "show logfile");
    }

    @Test
    public void deleteTest(){
        Backlog backlog = new Backlog("Keine Vision", "Keine Beschreibung");
        BacklogDAO backlogDAO = new BacklogDAO();
        backlogDAO.insertEntity(backlog);
        backlogDAO.deleteEntity(backlog.getId());
        Assert.assertNull(backlog);
    }
}

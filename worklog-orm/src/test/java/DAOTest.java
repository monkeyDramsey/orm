import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.BacklogDAO;
import swt6.orm.dao.UserStoryDAO;
import swt6.orm.domain.Backlog;
import swt6.orm.domain.UserStory;

public class DAOTest {

    @Test
    public void insertTest(){
        Backlog backlog = new Backlog("log", "show logfile");
        BacklogDAO backlogDAO = new BacklogDAO();
        backlogDAO.insertEntity(backlog);
        Assert.assertNotNull(backlog);
    }

    @Test
    public void updateTest(){
        UserStory userStory = new UserStory("title", "description", 4L);
        UserStoryDAO userStoryDAO = new UserStoryDAO();
        userStory.setTitle("changed");
        userStoryDAO.updateEntity(userStory);
        Assert.assertNotEquals(userStory.getTitle(), "title");
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

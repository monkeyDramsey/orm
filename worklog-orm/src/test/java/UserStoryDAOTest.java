import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.UserStoryDAO;
import swt6.orm.domain.UserStory;

public class UserStoryDAOTest {

    @Test
    public void insertTest(){
        UserStory userStory = new UserStory("title", "description", 4L);
        UserStoryDAO userStoryDAO = new UserStoryDAO();
        userStoryDAO.insertEntity(userStory);
        Assert.assertNotNull(userStory);
    }

    @Test
    public void updateTest(){
        UserStory userStory = new UserStory("title", "description", 4L);
        UserStoryDAO userStoryDAO = new UserStoryDAO();
        userStory.setTitle("new");
        userStoryDAO.updateEntity(userStory);
        Assert.assertNotEquals(userStory.getTitle(), "title");
    }

    @Test
    public void deleteTest(){
        UserStory userStory = new UserStory("title", "description", 4L);
        UserStoryDAO userStoryDAO = new UserStoryDAO();
        userStoryDAO.insertEntity(userStory);
        userStoryDAO.deleteEntity(userStory.getId());
        Assert.assertNull(userStory);
    }
}

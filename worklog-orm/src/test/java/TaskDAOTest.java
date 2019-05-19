import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.TaskDAO;
import swt6.orm.domain.Task;

public class TaskDAOTest {

    @Test
    public void insertTest(){
        Task task = new Task("new", "add entries", 3L, "done");
        TaskDAO taskDAO = new TaskDAO();
        taskDAO.insertEntity(task);
        Assert.assertNotNull(task);
    }

    @Test
    public void updateTest(){
        Task task = new Task("new", "add entries", 3L, "done");
        TaskDAO taskDAO = new TaskDAO();
        task.setTitle("test me");
        taskDAO.updateEntity(task);
        Assert.assertNotEquals(task.getTitle(), "new");
    }

    @Test
    public void deleteTest() {
        Task task = new Task("new", "add entries", 3L, "done");
        TaskDAO taskDAO = new TaskDAO();
        taskDAO.insertEntity(task);
        taskDAO.deleteEntity(task.getId());
        Assert.assertNull(task);
    }
}

import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.SprintDAO;
import swt6.orm.domain.Sprint;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SprintDAOTest {

    @Test
    public void insertTest(){
        Sprint sprint1 = new Sprint(LocalDate.of(2019, 01, 05), LocalDate.of(2019, 01, 25));
        SprintDAO issueDAO = new SprintDAO();
        issueDAO.insertEntity(sprint1);
        Assert.assertNotNull(sprint1);
    }

    @Test
    public void updateTest(){
        Sprint sprint1 = new Sprint(LocalDate.of(2019, 01, 05), LocalDate.of(2019, 01, 25));
        SprintDAO sprintDAO = new SprintDAO();
        sprint1.setEndDate(LocalDate.now());
        sprintDAO.updateEntity(sprint1);
        Assert.assertEquals(sprint1.getStartDate(), sprint1.getEndDate());
    }

    @Test
    public void deleteTest(){
        Sprint sprint1 = new Sprint(LocalDate.of(2019, 01, 05), LocalDate.of(2019, 01, 25));
        SprintDAO sprintDAO = new SprintDAO();
        sprintDAO.insertEntity(sprint1);
        sprintDAO.deleteEntity(sprint1.getId());
        Assert.assertNull(sprint1);
    }
}

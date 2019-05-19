package swt6.orm.jpa;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import swt6.orm.dao.*;
import swt6.orm.domain.*;
import swt6.orm.statistic.Statistics;
import swt6.util.JpaUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WorkLogManager {

	@SuppressWarnings("unused")
	private static void insertEmployee1(Employee empl) {
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("WorklogPU"); // name aus persistence.xml
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(empl);
            tx.commit();
        } catch (Exception e){
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            throw e;
        } finally {
            if(em != null){
                em.close();
            }
            emFactory.close();
        }
	}

	private static <T> T saveEntity(T entity) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager(); //erzeugt auto transaction
			entity = em.merge(entity); //merge verwenden - spart arbeit
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return entity;
	}


	public static void makeSomeTestData(){
		Employee employee = new Employee("Max", "Mustermann", LocalDate.of(1988, 01, 01));

		Project project = new Project("Uebung02");
		project.addMemmber(employee);

		Backlog backlog = new Backlog("Keine Vision", "Keine Beschreibung");
		project.addBacklog(backlog);

		Sprint sprint1 = new Sprint(LocalDate.of(2019, 01, 05), LocalDate.of(2019, 01, 25));
		Sprint sprint2 = new Sprint(LocalDate.of(2019, 02, 05), LocalDate.of(2019, 02, 25));
		project.addSprint(sprint1);
		project.addSprint(sprint2);

		UserStory userStory = new UserStory("title", "description", 4L);
		backlog.addUserStory(userStory);
		sprint1.addUserStory(userStory);

		Task task = new Task("new", "add entries", 3L, "done");
		Issue issue = new Issue("no title", "no description", 3L, "done", LocalDateTime.now(), LocalDateTime.now().plusHours(5), "high");
        Issue issue2 = new Issue("with title", "with description", 2L, "done", LocalDateTime.now(), LocalDateTime.now().plusHours(3), "high");
		userStory.addTask(task);
		userStory.addTask(issue);
		userStory.addTask(issue2);

		WorkLogManager.saveEntity(project);
	}

	static String promptFor(BufferedReader in, String p) {
		System.out.print(p + "> ");
		System.out.flush();
		try {
			return in.readLine();
		}
		catch (Exception e) {
			return promptFor(in, p);
		}
	}

	public static void main(String[] args) {
		makeSomeTestData();

		Statistics statistics = new Statistics();
		statistics.printAvarageTaskDuration();
		//statistics.printEstimateDurationRation(5);
		statistics.printAverageCompletedStoriesPerSprint(2);
		statistics.printIncompleteTaskForSprint(2);


		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
		String availEntity = "Backlog, Sprint, UserStory, Task, Issue, quit";
		System.out.println(availEntity);
		String userCmd = promptFor(in, "");

		BacklogDAO backlogDAO = new BacklogDAO();
		SprintDAO sprintDAO = new SprintDAO();
		TaskDAO taskDAO = new TaskDAO();
		UserStoryDAO userStoryDAO = new UserStoryDAO();
		IssueDAO issueDAO = new IssueDAO();

		while (!userCmd.equals("quit")) {

			switch (userCmd) {
				case "Backlog":
					backlogDAO.insertEntity(new Backlog(
							promptFor(in, "vision"),
							promptFor(in, "description")
						)
					);
					break;

				case "Sprint":
					sprintDAO.insertEntity(new Sprint(
							LocalDate.parse(
									promptFor(in, "from (dd.mm.yyyy)"), formatter),
							LocalDate.parse(
									promptFor(in, "to (dd.mm.yyyy)"), formatter)
							)
					);
					break;

				case "UserStory":
					UserStory userStory = new UserStory();
					userStory.setTitle(promptFor(in, "title"));
					userStory.setDescription(promptFor(in, "description"));
					userStory.setEstimate(Long.parseLong(promptFor(in, "estimate")));
					userStoryDAO.insertEntity(userStory);
					break;

				case "Task":
					Task task = new Task(
							promptFor(in, "title"),
							promptFor(in, "description"),
							Long.parseLong(promptFor(in, "estimate")),
							promptFor(in, "open, inProgress or done")
					);
					taskDAO.insertEntity(task);
					break;

				case "Issue":
					Issue issue = new Issue(
							promptFor(in, "title"),
							promptFor(in, "description"),
							Long.parseLong(promptFor(in, "estimate")),
							promptFor(in, "open, inProgress or done"),
							LocalDateTime.parse(
									promptFor(in, "from (dd.mm.yyyy)"), formatter),
							LocalDateTime.parse(
									promptFor(in, "from (dd.mm.yyyy)"), formatter),
							promptFor(in, "low, medium or high")
					);
					issueDAO.insertEntity(issue);
					break;
			}


			System.out.println(availEntity);
			userCmd = promptFor(in, "");
		}

	}
}
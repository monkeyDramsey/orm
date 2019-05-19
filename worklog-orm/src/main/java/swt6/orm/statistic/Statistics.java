package swt6.orm.statistic;

import swt6.orm.dao.IssueDAO;
import swt6.orm.dao.SprintDAO;
import swt6.orm.domain.Issue;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Statistics {

    private long avgDuration = 0;

    public void printAvarageTaskDuration(){
        IssueDAO issueDAO = new IssueDAO();
        List<Issue> issueList = issueDAO.getIssues();
        if(issueList != null){
            for(Issue i : issueList)
                avgDuration += Duration.between(i.getFoundDate(), i.getFixedDate()).toMillis();
            avgDuration /= 3600000;
            System.out.println("Avarage Time for solving Issue: " + avgDuration / issueList.size());
        } else {
            System.out.println("No issues open!");
        }
    }

    public void printEstimateDurationRation(long id){
        IssueDAO issueDAO = new IssueDAO();
        Issue issue = issueDAO.findById(id);
        long duration = 0;
        duration += Duration.between(issue.getFoundDate(), issue.getFixedDate()).toMillis();
        duration /= 3600000;
        long estimateTime = issue.getEstimation();
        System.out.println("ration between duration and estimate time: " + estimateTime + "/" + duration);
    }

    public void printAverageCompletedStoriesPerSprint(long id){
        SprintDAO sprintDAO = new SprintDAO();
        Sprint sprint = sprintDAO.findById(id);
        int count = 0;
        Set<UserStory> userStories = sprint.getUserStories();
        for(UserStory u : userStories){
            for(Task t : u.getTaskEntries()) {
                if (t.getState().equals("done")) {
                    count++;
                }
            }
        }
        System.out.println(count + " are done for sprint " + sprint.getId());
    }

    public void printIncompleteTaskForSprint(long id){
        SprintDAO sprintDao = new SprintDAO();
        Sprint sprint = sprintDao.findById(id);
        if(LocalDate.now().isAfter(sprint.getEndDate())){
            int count = 0;
            Set<UserStory> userStories = sprint.getUserStories();
            for(UserStory u : userStories){
                for(Task t : u.getTaskEntries()){
                    if(!t.getState().equals("done")){
                        count++;
                    }
                }
                System.out.println(count + " are done for sprint " + sprint.getId());
            }
        }
    }

    public long getDuration() {
        return avgDuration;
    }

    public void setDuration(long duration) {
        this.avgDuration = duration;
    }
}

package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserStory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Long estimate;

    @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Task> taskEntries = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Sprint sprint;

    public void addTask(Task task){
        if(task == null)
            throw new IllegalArgumentException("Null Task");
        this.taskEntries.add(task);
        task.setUserStory(this);
    }

    public void removeTask(Task task){
        if(task == null)
            throw new IllegalArgumentException("Null Task");
        this.taskEntries.remove(task);
        task.setUserStory(null);
    }

    public UserStory() {
    }

    public UserStory(String title, String description, Long estimate) {
        this.title = title;
        this.description = description;
        this.estimate = estimate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEstimate() {
        return estimate;
    }

    public void setEstimate(Long estimate) {
        this.estimate = estimate;
    }

    public Set<Task> getTaskEntries() {
        return taskEntries;
    }

    public void setTaskEntries(Set<Task> taskEntries) {
        this.taskEntries = taskEntries;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public String toString() {
        return "UserStory{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", estimate=" + estimate +
                '}';
    }
}

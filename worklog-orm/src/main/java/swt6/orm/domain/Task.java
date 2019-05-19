package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Long estimation;
    private Stati status;
    private String state;

    public enum Stati {
        open, inProgress, done
    }

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LogbookEntry> logbookEntrySet = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserStory userStory;

    public void addLogbookEntry(LogbookEntry logbookEntry) {
        if (logbookEntry == null)
            throw new IllegalArgumentException("Null LogbookEntry");
        logbookEntry.setTask(this);
        this.logbookEntrySet.add(logbookEntry);
    }

    public void removeLogbookEntry(LogbookEntry logbookEntry) {
        if (logbookEntry == null)
            throw new IllegalArgumentException("Null LogbookEntry");
        this.logbookEntrySet.remove(logbookEntry);
        logbookEntry.setTask(null);
    }

    public Task(){}

    public Task(String title, String description, Long estimate, String status) {
        this.title = title;
        this.description = description;
        this.estimation = estimate;
        this.state = status;
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

    public Long getEstimation() {
        return estimation;
    }

    public void setEstimation(Long estimation) {
        this.estimation = estimation;
    }

    public Set<LogbookEntry> getLogbookEntrySet() {
        return logbookEntrySet;
    }

    public void setLogbookEntrySet(Set<LogbookEntry> logbookEntrySet) {
        this.logbookEntrySet = logbookEntrySet;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", estimation=" + estimation +
                '}';
    }
}

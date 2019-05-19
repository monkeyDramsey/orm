package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sprint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserStory> userStories = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Project project;

    public void addUserStory(UserStory userStory){
        if(userStory == null)
            throw new IllegalArgumentException("Null UserStory");
        this.userStories.add(userStory);
        userStory.setSprint(this);
    }

    public void removeUserStory(UserStory userStory){
        if(userStory == null)
            throw new IllegalArgumentException("Null UserStory");
        this.userStories.remove(userStory);
        userStory.setSprint(null);
    }

    public Sprint() {
    }

    public Sprint(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Backlog implements Serializable {

    public Backlog() {
    }

    public Backlog(String vision, String description) {
        this.vision = vision;
        this.description = description;
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String vision;
    private String description;

    @OneToMany(mappedBy = "backlog", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserStory> userStories = new HashSet<>();

    public void addUserStory(UserStory userStory){
        if(userStory == null)
            throw new IllegalArgumentException("Null UserStory");
        userStories.add(userStory);
        userStory.setBacklog(this);
    }

    public void removeUserStory(UserStory userStory){
        if(userStory == null)
            throw new IllegalArgumentException("Null UserStory");
        userStories.remove(userStory);
        userStory.setBacklog(null);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    @Override
    public String toString() {
        return "Backlog{" +
                "vision='" + vision + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
//version 2
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//version 3
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee implements Serializable {

    //hibernate würde auch ohne Serializable laufen

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) //wetrte für ID eigene tabelle
    private long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth; //zb @Transient -> wird nicht in DB geschrieben

    @OneToMany(mappedBy = "employee", //mappedBy durch welches attribut wird bidirectionale klasse abgeleitet - mit strg + click kann in klasse geswitcht werden
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //wie werden operationen an ref objekte weiter gegeben
    @Fetch(FetchMode.SELECT) //wie wird query auf sql abgebildet version 1: FetchMode.JOIN -> wiederspricht lazy, macht keinen sinn, wäre wie eager
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    //Version 2: @Embedded
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="ADDRESS_ID") // set name to join column
    private Address address;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY, cascade = CascadeType.MERGE) //members gegenstück zu projects
    private Set<Project> projects = new HashSet<>();

    //classes that are persisted by hibernate must have a default constructor
    public Employee() {
        //nothing
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project){
        if(project == null){
            throw new IllegalArgumentException("Project is null");
        }
        project.getMembers().add(this);
        this.projects.add(project);
    }

    public void removeProject(Project project){
        if(project == null){
            throw new IllegalArgumentException("Project is null");
        }
        project.getMembers().remove(this);
        this.projects.remove(project);
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // alt + einfg
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public void addLogbookEntry(LogbookEntry entry){
        if(entry.getEmployee() != null){
            entry.getEmployee().getLogbookEntries().remove(entry);
        }
        //set bidirection link
        this.logbookEntries.add(entry);
        entry.setEmployee(this);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

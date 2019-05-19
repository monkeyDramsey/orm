package swt6.orm.domain;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Issue extends Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime foundDate;
    private LocalDateTime fixedDate;
    private CustomSeverity severityEnum;
    private String severity;

    public enum CustomSeverity {
        low, medium, high
    }

    public Issue(){}

    public Issue(String title, String description, Long estimate, String status, LocalDateTime foundDate, LocalDateTime fixedDate, String severity){
        super(title, description, estimate, status);
        this.foundDate = foundDate;
        this.fixedDate = fixedDate;
        this.severity = severity;
    }

    public LocalDateTime getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(LocalDateTime foundDate) {
        this.foundDate = foundDate;
    }

    public LocalDateTime getFixedDate() {
        return fixedDate;
    }

    public void setFixedDate(LocalDateTime fixedDate) {
        this.fixedDate = fixedDate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "foundDate=" + foundDate +
                ", fixedDate=" + fixedDate +
                ", severity=" + severity +
                '}';
    }
}

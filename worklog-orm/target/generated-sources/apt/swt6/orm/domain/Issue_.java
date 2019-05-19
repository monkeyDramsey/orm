package swt6.orm.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import swt6.orm.domain.Issue.CustomSeverity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Issue.class)
public abstract class Issue_ extends swt6.orm.domain.Task_ {

	public static volatile SingularAttribute<Issue, String> severity;
	public static volatile SingularAttribute<Issue, LocalDateTime> fixedDate;
	public static volatile SingularAttribute<Issue, LocalDateTime> foundDate;
	public static volatile SingularAttribute<Issue, CustomSeverity> severityEnum;

	public static final String SEVERITY = "severity";
	public static final String FIXED_DATE = "fixedDate";
	public static final String FOUND_DATE = "foundDate";
	public static final String SEVERITY_ENUM = "severityEnum";

}


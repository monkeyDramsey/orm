package swt6.orm.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import swt6.orm.domain.Task.Stati;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, Long> estimation;
	public static volatile SetAttribute<Task, LogbookEntry> logbookEntrySet;
	public static volatile SingularAttribute<Task, String> description;
	public static volatile SingularAttribute<Task, UserStory> userStory;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, String> state;
	public static volatile SingularAttribute<Task, String> title;
	public static volatile SingularAttribute<Task, Stati> status;

	public static final String ESTIMATION = "estimation";
	public static final String LOGBOOK_ENTRY_SET = "logbookEntrySet";
	public static final String DESCRIPTION = "description";
	public static final String USER_STORY = "userStory";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String TITLE = "title";
	public static final String STATUS = "status";

}


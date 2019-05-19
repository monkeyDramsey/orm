package swt6.orm.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface IDAO <T>{
    EntityManagerFactory emFactory =
            Persistence.createEntityManagerFactory("WorklogPU");

    void insertEntity(T entity);

    void deleteEntity(long id);

    T updateEntity(T entity);

}

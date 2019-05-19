
package swt6.orm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import swt6.orm.domain.Employee;
import swt6.util.HibernateUtil;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EmployeeManager {

  static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }

  // Version 1:
  public static void saveEmployee1(Employee empl) {
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();

    session.save(empl);

    tx.commit();
    session.close();
    sessionFactory.close();
  }

  // Version 2:
  public static void saveEmployee2(Employee empl) {
    Session session = HibernateUtil.getSession();
    Transaction tx = session.beginTransaction();

    session.save(empl);

    tx.commit();
    HibernateUtil.closeSession();
  }

  // Version 3:
  public static void saveEmployee(Employee empl) {
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();
    session.save(empl);
    tx.commit();
  }

  private static boolean deleteEmployee(long emplId) {
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();

    //variant 1
//    Employee empl = session.find(Employee.class, emplId);
//    if(empl!= null){
//      session.remove(empl);
//    }
//    boolean deleted = empl != null;

    //variant 2:
    Query<?> delQry = session.createQuery("delete from Employee e where e.id = :id");
    delQry.setParameter("id", emplId);
    boolean deleted = delQry.executeUpdate() > 0;

    tx.commit();
    return deleted;
  }

  private static boolean updateEmployee(long emplId, String firstName,
                                        String lastName, LocalDate dob) {
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();
    Employee empl = session.find(Employee.class, emplId);
    if(empl != null) {
      empl.setFirstName(firstName);
      empl.setLastName(lastName);
      empl.setDateOfBirth(dob);
    }
    tx.commit();
    return empl != null;
  }

  private static Employee findEmployee(long emplId) {
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();

    Employee empl = session.find(Employee.class, emplId);
    tx.commit();
    return empl;
  }

  private static List<Employee> findByLastName(String lastName) {
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();
    Query<Employee> qry = session.createQuery("select e from Employee e where lastName like :lastName", Employee.class);
    qry.setParameter("lastName", "%" + lastName + "%"); //für sql -> kann irgendwo im text vorkommen
    List<Employee> empls = qry.getResultList();

    tx.commit();
    return empls;
  }

  private static List<Employee> getAllEmployees() {
    Session session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();

    List<Employee> empls = session.createQuery("select e from Employee e", Employee.class).getResultList();
    tx.commit();
    return empls;
  }

  public static void main(String[] args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: quit, list, insert, findById, " +
            "findByLastName, update, delete";

    System.out.println("Hibernate Employee Admin");
    System.out.println(availCmds);
    String userCmd = promptFor(in, "");

    try {

      while (!userCmd.equals("quit")) {

        switch (userCmd) {

          case "list":
            for (Employee empl : getAllEmployees())
              System.out.println(empl);
            break;

          case "findById":
            System.out.println(findEmployee(Long.parseLong(promptFor(in, "id"))));
            break;

          case "findByLastName":
            for (Employee empl : findByLastName(promptFor(in, "lastName")))
              System.out.println(empl);
            break;

          case "insert":
            try {
              saveEmployee(new Employee(promptFor(in, "firstName"),
                      promptFor(in, "lastName"),
                      LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"),
                              formatter)));
            }
            catch (DateTimeParseException e) {
              System.err.println("Invalid date format.");
            }

            break;

          case "update":
            try {
              boolean success =
                      updateEmployee(Long.parseLong(promptFor(in, "id")),
                              promptFor(in, "firstName"),
                              promptFor(in, "lastName"),
                              LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"),
                                      formatter));
              System.out.println(success ? "employee updated" : "employee not found");
            }
            catch (DateTimeParseException e) {
              System.err.println("Invalid date format.");
            }
            break;

          case "delete":
            boolean success = deleteEmployee(Long.parseLong(promptFor(in, "id")));
            System.out.println(success ? "employee deleted" : "employee not found");
            break;

          default:
            System.out.println("ERROR: invalid command");
            break;
        } // switch

        System.out.println(availCmds);
        userCmd = promptFor(in, "");
      } // while

    } // try
    catch (Exception ex) {
      ex.printStackTrace();
    } // catch
    finally {
      //ensure closing of session factory
      HibernateUtil.closeSessionFactory();
    }
  }
}

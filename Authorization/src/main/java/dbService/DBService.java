package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBService {
    private SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getConfigurationMysql();
        sessionFactory = getSessionFactory(configuration);
    }

    public UsersDataSet getUserDataSet(String login) throws DBServiceException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO usersDAO = new UsersDAO(session);
            UsersDataSet usersDataSet = usersDAO.getUsers(login);
            session.close();
            return usersDataSet;
        } catch (HibernateException e){
            throw new DBServiceException(e);
        }

    }


    public long addUserDataSet(String login, String password) throws DBServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO usersDAO = new UsersDAO(session);
            long id = usersDAO.insertUser(login, password);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e){
            throw new DBServiceException(e);
        }
    }

    private SessionFactory getSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getConfigurationMysql() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_users");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "pass");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }

}

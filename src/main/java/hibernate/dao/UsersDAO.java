package hibernate.dao;

import datasets.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public User getUserById(long id) throws HibernateException {
        return (User) session.get(User.class, id);
    }

    public User getUserByLogin(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(User.class);
        return (User) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public long getIdByLogin(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(User.class);
        return ((User) criteria.add(Restrictions.eq("login", login)).uniqueResult()).getId();
    }

    public long insertUser(String login, String password, String email) throws HibernateException {
        return (Long) session.save(new User(login, password, email));
    }

    public void createTable() throws HibernateException {
    }

    public void dropTable() throws HibernateException {
    }

}

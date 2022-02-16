package org.itstep;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateRunner {
    private static Session session = null;

    public static void main(String[] args) {
        Configuration conf = new Configuration().configure("org/itstep/hibernate.cfg.xml");
        StandardServiceRegistryBuilder builder = new
                StandardServiceRegistryBuilder().applySettings(conf.getProperties());
        StandardServiceRegistry registry = builder.build();
        SessionFactory sessionFactory = conf.buildSessionFactory(registry);
        session = sessionFactory.openSession();
        //getUserById(45);
        //addUserById();
        //deleteUser();
        //updateUser();
        getByName();

        session.close();
        sessionFactory.close();
        builder.destroy(registry);
    }
    private static void getUserById(int id) {
        session.beginTransaction();
        Query query = session.createQuery("from UserEntity where id = :id");
        query.setParameter("id", id);
        List list = query.list();
        list.stream().forEach(o -> System.out.println(((UserEntity) o).getSurname()));
        session.getTransaction().commit();
    }
    private static void addUserById(){
        session.beginTransaction();
        UserEntity user = new UserEntity();
        user.setName("admin");
        user.setSurname("admin1");
        user.setPatronymic("1admin");
        user.setLogin("add");
        user.setPassword("admin123");
        session.save(user);
        session.getTransaction().commit();
    }
    public static void deleteUser(){
        session.beginTransaction();
        UserEntity user = (UserEntity) session.get(UserEntity.class, 5488);
        session.delete(user);
        session.getTransaction().commit();
    }
    private static void updateUser(){
        session.beginTransaction();
        UserEntity user = (UserEntity) session.get(UserEntity.class, 10);
        user.setName("UpdateUser");
        session.update(user);
        session.getTransaction().commit();
    }
    private  static void getByName(){
        List<UserEntity> users = session.createCriteria(UserEntity.class)
                .add(Restrictions.eq("name","Антон")).list();
        users.stream().forEach(u-> System.out.println(u.getName()));
    }
}

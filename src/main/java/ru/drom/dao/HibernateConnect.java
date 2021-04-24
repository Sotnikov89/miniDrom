package ru.drom.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateConnect {

    private final StandardServiceRegistry registry;
    private final SessionFactory sf;
    private final Session session;
    final Transaction tx;

    private HibernateConnect() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sf.openSession();
        tx = session.beginTransaction();
    }

    public static HibernateConnect instOf() {
        return new HibernateConnect();
    }

    public <T> T sessionMethodsWithReturn(final Function<Session, T> command) {
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void sessionVoidMethods(final Consumer<Session> command) {
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}

package ru.drom.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.drom.model.Advert;

import java.time.LocalDate;
import java.util.List;

public class AdRepostiroty{

    private final StandardServiceRegistry registry;
    private final SessionFactory sf;

    private AdRepostiroty() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static AdRepostiroty instOf() {
        return new AdRepostiroty();
    }

    public List<Advert> getAdvThisDay(){
        List<Advert> adverts;
        Session session = sf.openSession();
        session.beginTransaction();
        adverts = session.createQuery("select adv from Advert adv " +
                "join fetch adv.user user join fetch user.city city " +
                "join fetch adv.model model join fetch model.make make " +
                "join fetch adv.typeBody typeBody where adv.created = :created")
                .setParameter("created", LocalDate.now()).list();
        session.getTransaction().commit();
        session.close();
        return adverts;
    }

    public List<Advert> getAdvWithPhoto(){
        List<Advert> adverts;
        Session session = sf.openSession();
        session.beginTransaction();
        adverts = session.createQuery("select adv from Advert adv " +
                "join fetch adv.user user join fetch user.city city " +
                "join fetch adv.model model join fetch model.make make " +
                "join fetch adv.typeBody typeBody where adv.photoId is not null")
                .list();
        session.getTransaction().commit();
        session.close();
        return adverts;
    }

    public List<Advert> getAdvByMake(String make){
        List<Advert> adverts;
        Session session = sf.openSession();
        session.beginTransaction();
        adverts = session.createQuery("select adv from Advert adv " +
                "join fetch adv.user user join fetch user.city city " +
                "join fetch adv.model model join fetch model.make make " +
                "join fetch adv.typeBody typeBody where make.name = :makeName")
                .setParameter("makeName", make).list();
        session.getTransaction().commit();
        session.close();
        return adverts;
    }
}

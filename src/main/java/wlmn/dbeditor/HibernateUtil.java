package wlmn.dbeditor;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    protected static void buildSessionFactory(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        buildSessionFactory();
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        if ( sessionFactory != null ) {
		  sessionFactory.close();
	    }
    }
}

Hibernate XML configuration file “`hibernate.cfg.xml`” is always put at the root of your project classpath, outside of any package. If you place this configuration file into a different directory, you may encounter the following error :

    Initial SessionFactory creation failed.org.hibernate.HibernateException:
    /hibernate.cfg.xml not found

    Exception in thread "main" java.lang.ExceptionInInitializerError
    	at com.mkyong.persistence.HibernateUtil.buildSessionFactory(HibernateUtil.java:25)
    	at com.mkyong.persistence.HibernateUtil.<clinit>(HibernateUtil.java:8)
    	at com.mkyong.common.App.main(App.java:11)
    Caused by: org.hibernate.HibernateException: /hibernate.cfg.xml not found
    	at org.hibernate.util.ConfigHelper.getResourceAsStream(ConfigHelper.java:147)
    	at org.hibernate.cfg.Configuration.getConfigurationInputStream(Configuration.java:1405)
    	at org.hibernate.cfg.Configuration.configure(Configuration.java:1427)
    	at org.hibernate.cfg.Configuration.configure(Configuration.java:1414)
    	at com.mkyong.persistence.HibernateUtil.buildSessionFactory(HibernateUtil.java:13)
    	... 2 more

To ask Hibernate look for your “`hibernate.cfg.xml`” file in other directory, you can modify the default Hibernate’s `SessionFactory` class by passing your “`hibernate.cfg.xml`” file path as an argument into the **configure()** method:

    SessionFactory sessionFactory = new Configuration()
    .configure("/com/mkyong/persistence/hibernate.cfg.xml")
    .buildSessionFactory();

    return sessionFactory;

## HibernateUtil.java

Full Example in `HibernateUtil.java`, to load “`hibernate.cfg.xml`” from directory “**/com/mkyong/persistence/**“.

    import org.hibernate.SessionFactory;
    import org.hibernate.cfg.Configuration;

    public class HibernateUtil {

    	private static final SessionFactory sessionFactory = buildSessionFactory();

    	private static SessionFactory buildSessionFactory() {
    		try {
    			// load from different directory
    			SessionFactory sessionFactory = new Configuration().configure(
    					"/com/mkyong/persistence/hibernate.cfg.xml")
    					.buildSessionFactory();

    			return sessionFactory;

    		} catch (Throwable ex) {
    			// Make sure you log the exception, as it might be swallowed
    			System.err.println("Initial SessionFactory creation failed." + ex);
    			throw new ExceptionInInitializerError(ex);
    		}
    	}

    	public static SessionFactory getSessionFactory() {
    		return sessionFactory;
    	}

    	public static void shutdown() {
    		// Close caches and connection pools
    		getSessionFactory().close();
    	}

    }

Done.

[http://www.mkyong.com/hibernate/how-to-load-hibernate-cfg-xml-from-different-directory/](http://www.mkyong.com/hibernate/how-to-load-hibernate-cfg-xml-from-different-directory/)

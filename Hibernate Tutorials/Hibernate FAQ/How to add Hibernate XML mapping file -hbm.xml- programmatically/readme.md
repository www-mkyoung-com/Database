Hibernate XML mapping file contains the mapping relationship between Java class and database table. This is always named as “xx.hbm.xml” and declared in the Hibernate configuration file “hibernate.cfg.xml”.

For example, the mapping file (hbm.xml) is declared in the “**mapping**” tag

    <?xml version="1.0" encoding="utf-8"?>
    <!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
    <hibernate-configuration>
    <session-factory>
      <property name="hibernate.bytecode.use_reflection_optimizer">false</property>
      <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
      <property name="hibernate.connection.password">password</property>
      <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/mkyong</property>
      <property name="hibernate.connection.username">root</property>
      <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
      <property name="show_sql">true</property>
      <mapping resource="com/mkyong/common/Stock.hbm.xml"></mapping>
    </session-factory>
    </hibernate-configuration>

## Add Hibernate’s mapping file (hbm.xml) programmatically

For any reasons you do not want to include the mapping file in `hibernate.cfg.xml`. Hibernate provides a method for developer to add mapping file programmatically.

Just modify the default Hibernate `SessionFactory` class by passing your “**hbm.xml**” file path as an argument into the `addResource()` method:

    SessionFactory sessionFactory = new Configuration()
       .addResource("com/mkyong/common/Stock.hbm.xml")
       .buildSessionFactory();

## HibernateUtil.java

Full Example of `HibernateUtil.java`, load Hibernate XML mapping file “xx.hbm.xml” programmatically.

    import org.hibernate.SessionFactory;
    import org.hibernate.cfg.Configuration;

    public class HibernateUtil {

    	private static final SessionFactory sessionFactory = buildSessionFactory();

    	private static SessionFactory buildSessionFactory() {
    		try {

    			SessionFactory sessionFactory = new Configuration()
    					.configure("/com/mkyong/persistence/hibernate.cfg.xml")
    					.addResource("com/mkyong/common/Stock.hbm.xml")
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

[http://www.mkyong.com/hibernate/how-to-add-hibernate-xml-mapping-file-hbm-xml-programmatically/](http://www.mkyong.com/hibernate/how-to-add-hibernate-xml-mapping-file-hbm-xml-programmatically/)

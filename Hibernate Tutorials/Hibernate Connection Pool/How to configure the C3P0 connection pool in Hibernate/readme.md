**Connection Pool**  
Connection pool is good for performance, as it prevents Java application create a connection each time when interact with database and minimizes the cost of opening and closing connections.

See [wiki connection pool](http://en.wikipedia.org/wiki/Connection_pool) explanation

Hibernate comes with internal connection pool, but not suitable for production use. In this tutorial, we show you how to integrate third party connection pool – C3P0, with Hibernate.

## 1\. Get hibernate-c3p0.jar

To integrate c3p0 with Hibernate, you need **hibernate-c3p0.jar**, get it from JBoss repository.

_File : pom.xml_

    <project ...>

    	<repositories>
    		<repository>
    			<id>JBoss repository</id>
    			<url>http://repository.jboss.org/nexus/content/groups/public/</url>
    		</repository>
    	</repositories>

    	<dependencies>

    		<dependency>
    			<groupId>org.hibernate</groupId>
    			<artifactId>hibernate-core</artifactId>
    			<version>3.6.3.Final</version>
    		</dependency>

    		<!-- Hibernate c3p0 connection pool -->
    		<dependency>
    			<groupId>org.hibernate</groupId>
    			<artifactId>hibernate-c3p0</artifactId>
    			<version>3.6.3.Final</version>
    		</dependency>

    	</dependencies>
    </project>

## 2\. Configure c3p0 propertise

To configure c3p0, puts the c3p0 configuration details in “**hibernate.cfg.xml**“, like this :

_File : hibernate.cfg.xml_

    <?xml version="1.0" encoding="utf-8"?>
    <!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
    <hibernate-configuration>
     <session-factory>
      <property name="hibernate.connection.driver_class">oracle.jdbc.driver.OracleDriver</property>
      <property name="hibernate.connection.url">jdbc:oracle:thin:@localhost:1521:MKYONG</property>
      <property name="hibernate.connection.username">mkyong</property>
      <property name="hibernate.connection.password">password</property>
      <property name="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</property>
      <property name="hibernate.default_schema">MKYONG</property>
      <property name="show_sql">true</property>

      <property name="hibernate.c3p0.min_size">5</property>
      <property name="hibernate.c3p0.max_size">20</property>
      <property name="hibernate.c3p0.timeout">300</property>
      <property name="hibernate.c3p0.max_statements">50</property>
      <property name="hibernate.c3p0.idle_test_period">3000</property>

      <mapping class="com.mkyong.user.DBUser"></mapping>
    </session-factory>
    </hibernate-configuration>

1.  hibernate.c3p0.min_size – Minimum number of JDBC connections in the pool. Hibernate default: 1
2.  hibernate.c3p0.max_size – Maximum number of JDBC connections in the pool. Hibernate default: 100
3.  hibernate.c3p0.timeout – When an idle connection is removed from the pool (in second). Hibernate default: 0, never expire.
4.  hibernate.c3p0.max_statements – Number of prepared statements will be cached. Increase performance. Hibernate default: 0 , caching is disable.
5.  hibernate.c3p0.idle_test_period – idle time in seconds before a connection is automatically validated. Hibernate default: 0

**Note**  
For detail about **hibernate-c3p0** configuration settings, please read [this](http://community.jboss.org/wiki/HowToConfigureTheC3P0ConnectionPool) article.

## Run it, output

Done, run it and see the following output :

![c3p0 connection pool in hibernate](http://www.mkyong.com/wp-content/uploads/2009/12/hibernate-c3p0-connection-pool.png)

During the connection initialize process, 5 database connections are created in connection pool, ready reuse for your web application.

[http://www.mkyong.com/hibernate/how-to-configure-the-c3p0-connection-pool-in-hibernate/](http://www.mkyong.com/hibernate/how-to-configure-the-c3p0-connection-pool-in-hibernate/)

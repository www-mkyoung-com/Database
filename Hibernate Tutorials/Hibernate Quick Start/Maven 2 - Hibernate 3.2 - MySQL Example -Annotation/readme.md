**Note**  
This article is outdated, and some information is no longer valid in latest Hibernate development. You should refer to this latest – [Maven 3 + Hibernate 3.6.3 + Oracle 11g Example (Annotation)](http://www.mkyong.com/hibernate/maven-3-hibernate-3-6-oracle-11g-example-annotation/) tutorial.

This tutorial will modify the previous [Maven 2 + Hibernate 3.2 + MySQL Example (XML mapping)](http://www.mkyong.com/hibernate/quick-start-maven-hibernate-mysql-example/), and replace the Hibernate XML mapping file with Annotation code.

Tools & technologies used in this article :

1.  Maven 2.2.1
2.  JDK 1.6.0_13
3.  Hibernate 3.2.3.GA
4.  MySQL 5.0

## 1\. Create project infrastructure

Create project infrastructure in [Maven + Hibernate (XML mapping file) + MySQL Example](http://www.mkyong.com/hibernate/quick-start-maven-hibernate-mysql-example/)

## 2\. Add JBoss repository

JBoss repository in `pom.xml` is required to download the Hibernate annotation library.

    <repositories>
       <repository>
         <id>JBoss repository</id>
         <url>http://repository.jboss.com/maven2/</url>
       </repository>
     </repositories>

## 3\. Add Hibernate annotation dependency

Add hibernate-annotations and hibernate-commons-annotations dependency in `pom.xml`.

    <dependency>
    	<groupId>hibernate-annotations</groupId>
    	<artifactId>hibernate-annotations</artifactId>
    	<version>3.3.0.GA</version>
    </dependency>

    <dependency>
    	<groupId>hibernate-commons-annotations</groupId>
    	<artifactId>hibernate-commons-annotations</artifactId>
    	<version>3.0.0.GA</version>
    </dependency>

_File : pom.xml_

    <project xmlns="http://maven.apache.org/POM/4.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
      http://maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.mkyong.common</groupId>
      <artifactId>HibernateExample</artifactId>
      <packaging>jar</packaging>
      <version>1.0-SNAPSHOT</version>
      <name>HibernateExample</name>
      <url>http://maven.apache.org</url>

      <repositories>
        <repository>
          <id>JBoss repository</id>
          <url>http://repository.jboss.com/maven2/</url>
        </repository>
      </repositories>

      <dependencies>

        <!-- MySQL database driver -->
    	<dependency>
    		<groupId>mysql</groupId>
    		<artifactId>mysql-connector-java</artifactId>
    		<version>5.1.9</version>
    	</dependency>

    	<!-- Hibernate core -->
    	<dependency>
    		<groupId>hibernate</groupId>
    		<artifactId>hibernate3</artifactId>
    		<version>3.2.3.GA</version>
    	</dependency>

    	<!-- Hibernate annotation -->
    	<dependency>
    		<groupId>hibernate-annotations</groupId>
    		<artifactId>hibernate-annotations</artifactId>
    		<version>3.3.0.GA</version>
    	</dependency>

    	<dependency>
    		<groupId>hibernate-commons-annotations</groupId>
    		<artifactId>hibernate-commons-annotations</artifactId>
    		<version>3.0.0.GA</version>
    	</dependency>

    	<!-- Hibernate library dependecy start -->
    	<dependency>
    		<groupId>dom4j</groupId>
    		<artifactId>dom4j</artifactId>
    		<version>1.6.1</version>
    	</dependency>

    	<dependency>
    		<groupId>commons-logging</groupId>
    		<artifactId>commons-logging</artifactId>
    		<version>1.1.1</version>
    	</dependency>

    	<dependency>
    		<groupId>commons-collections</groupId>
    		<artifactId>commons-collections</artifactId>
    		<version>3.2.1</version>
    	</dependency>

    	<dependency>
    		<groupId>cglib</groupId>
    		<artifactId>cglib</artifactId>
    		<version>2.2</version>
    	</dependency>
    	<!-- Hibernate library dependecy end -->

    	<dependency>
    		<groupId>javax.transaction</groupId>
    		<artifactId>jta</artifactId>
    		<version>1.1</version>
    	</dependency>

      </dependencies>
    </project>

## 4\. Update project classpath

Issue “`mvn eclipse:eclipse`” in command prompt to download the dependency library and update the Eclipse’s project classpath.

## 5\. Update HibernateUtil.java

Update “`HibernateUtil`” to use “**AnnotationConfiguration**” instead of “**Configuration**” to build the Hibernate session factory.

Previously is using “Configuration” – For Hibernate XML mapping file

    return new Configuration().configure().buildSessionFactory();

Change it to “AnnotationConfiguration” – For Hibernation annotation support

    return new AnnotationConfiguration().configure().buildSessionFactory();

_File : HibernateUtil.java_

    package com.mkyong.persistence;

    import org.hibernate.SessionFactory;
    import org.hibernate.cfg.AnnotationConfiguration;

    public class HibernateUtil {

        private static final SessionFactory sessionFactory = buildSessionFactory();

        private static SessionFactory buildSessionFactory() {
            try {
                // Create the SessionFactory from hibernate.cfg.xml
                return new AnnotationConfiguration().configure().buildSessionFactory();

            }
            catch (Throwable ex) {
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

## 6\. Update Model class

Update “Stock.java” to use annotation as follow :

Stock.java

    package com.mkyong.common;

    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import static javax.persistence.GenerationType.IDENTITY;
    import javax.persistence.Id;
    import javax.persistence.Table;
    import javax.persistence.UniqueConstraint;

    @Entity
    @Table(name = "stock", catalog = "mkyong", uniqueConstraints = {
    		@UniqueConstraint(columnNames = "STOCK_NAME"),
    		@UniqueConstraint(columnNames = "STOCK_CODE") })
    public class Stock implements java.io.Serializable {

    	private Integer stockId;
    	private String stockCode;
    	private String stockName;

    	public Stock() {
    	}

    	public Stock(String stockCode, String stockName) {
    		this.stockCode = stockCode;
    		this.stockName = stockName;
    	}

    	@Id
    	@GeneratedValue(strategy = IDENTITY)
    	@Column(name = "STOCK_ID", unique = true, nullable = false)
    	public Integer getStockId() {
    		return this.stockId;
    	}

    	public void setStockId(Integer stockId) {
    		this.stockId = stockId;
    	}

    	@Column(name = "STOCK_CODE", unique = true, nullable = false, length = 10)
    	public String getStockCode() {
    		return this.stockCode;
    	}

    	public void setStockCode(String stockCode) {
    		this.stockCode = stockCode;
    	}

    	@Column(name = "STOCK_NAME", unique = true, nullable = false, length = 20)
    	public String getStockName() {
    		return this.stockName;
    	}

    	public void setStockName(String stockName) {
    		this.stockName = stockName;
    	}

    }

## 7\. Delete existing Hibernate XML mapping file

Delete existing Hibernate XML mapping file – “Stock.hbm.xml”, this is no longer require.

## 8\. Update Hibernate configuration file

Update the Hibernate configuration file – hibernate.cfg.xml.

Previously it had the Hibernate XML mapping file with “mapping resource” tag

    <mapping resource="com/mkyong/common/Stock.hbm.xml"></mapping>

Change it to model class with “mapping class” tag

    <mapping class="com.mkyong.common.Stock"></mapping>

_File : hibernate.cfg.xml_

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
            <mapping class="com.mkyong.common.Stock"></mapping>
        </session-factory>
    </hibernate-configuration>

## 9\. Review project structure

Sound like few files were modified, review it and make sure the folder structure as follow :

![maven_hibernate_annotation_mysql](http://www.mkyong.com/wp-content/uploads/2009/12/maven_hibernate_annotation_mysql.jpg)

## 10\. Run it and see output

Run your App.java, it will insert a new record into “Stock” table. The result should be same with previous Hibernate XML mapping file example.

    Maven + Hibernate + MySQL
    ...
    Hibernate: insert into mkyong.stock (STOCK_CODE, STOCK_NAME) values (?, ?)

Done.

[http://www.mkyong.com/hibernate/maven-hibernate-annonation-mysql-example/](http://www.mkyong.com/hibernate/maven-hibernate-annonation-mysql-example/)

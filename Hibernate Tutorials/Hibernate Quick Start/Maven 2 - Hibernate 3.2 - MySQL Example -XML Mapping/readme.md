**Note**  
This article is outdated, please refer to the latest Hibernate 3.6 tutorial at – [Maven 3 + Hibernate 3.6 + Oracle 11g Example (XML Mapping)](http://www.mkyong.com/hibernate/maven-3-hibernate-3-6-oracle-11g-example-xml-mapping/).

This quick guide show you how to use Maven to generate a simple Java project, and uses Hibernate to insert a record into MySQL database.

Tools & technologies used in this article :

1.  Maven 2.2.1
2.  JDK 1.6.0_13
3.  Hibernate 3.2.3.GA
4.  MySQL 5.0

## 1\. Table Creation

MySQL script to create a “stock” table.

    DROP TABLE IF EXISTS `stock`;
    CREATE TABLE `stock` (
      `STOCK_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
      `STOCK_CODE` VARCHAR(10) NOT NULL,
      `STOCK_NAME` VARCHAR(20) NOT NULL,
      PRIMARY KEY (`STOCK_ID`) USING BTREE,
      UNIQUE KEY `UNI_STOCK_NAME` (`STOCK_NAME`),
      UNIQUE KEY `UNI_STOCK_ID` (`STOCK_CODE`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

## 2\. Generate project structure with Maven

In command prompt, Issue “`mvn archetype:generate`“, choose project #15 to create a quick start Maven’s java project, then fill in your project’s information – groupId , artifactId and package.

    E:\workspace>mvn archetype:generate
    [INFO] Scanning for projects...
    ...
    Choose a number:
    (1/2/3....) 15: : 15
    ...
    Define value for groupId: : com.mkyong.common
    Define value for artifactId: : HibernateExample
    Define value for version:  1.0-SNAPSHOT: :
    Define value for package:  com.mkyong.common: : com.mkyong.common
    [INFO] OldArchetype created in dir: E:\workspace\HibernateExample
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESSFUL
    [INFO] ------------------------------------------------------------------------

## 3\. Convert to Eclipse project

Issue “`mvn eclipse:eclipse`” to convert the newly generated project to Eclipse’s style project

## 4\. Import converted project into Eclipse IDE

In Eclipse IDE, Choose File –> Import –> General folder, Existing Projects into Workspace –>Choose your project folder location. Done

## 5\. Create a resources folder

Create a resources folder under “**src/main**” folder, “**/src/main/resources**” , later all the Hibernate’s xml files will put here. Maven will treat all files in this folder as resources files, and copy it to output classes automatically.

## 6\. Review project structure

Make sure the folder structure as following

![hibernate-example-1](http://www.mkyong.com/wp-content/uploads/2009/12/hibernate-example-1.jpg)

Maven will generate all the Java’s standard folders structure for you (beside resources folder, quick start archetype #15 does not contains the resources folder)

## 7\. Add Hibernate and MySQL dependency

Modify the Maven’s `pom.xml` file, add support for Hibernate and MySQL. Hibernate is required dom4j, commons-logging, commons-collections and cglib as dependency library, add it.

_File : pom.xml_

    <project ...>
    <modelVersion>4.0.0</modelVersion>
      <groupId>com.mkyong.common</groupId>
      <artifactId>HibernateExample</artifactId>
      <packaging>jar</packaging>
      <version>1.0-SNAPSHOT</version>
      <name>HibernateExample</name>
      <url>http://maven.apache.org</url>
      <dependencies>

        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>3.8.1</version>
          <scope>test</scope>
        </dependency>

            <!-- MySQL database driver -->
    	<dependency>
    		<groupId>mysql</groupId>
    		<artifactId>mysql-connector-java</artifactId>
    		<version>5.1.9</version>
    	</dependency>

    	<!-- Hibernate framework -->
    	<dependency>
    		<groupId>hibernate</groupId>
    		<artifactId>hibernate3</artifactId>
    		<version>3.2.3.GA</version>
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

Issue the “`mvn eclipse:eclipse`“, Maven will download all Hibernate and MySQL libraries automatically and put into Maven’s local repository. At the same time, Maven will add the downloaded libraries into Eclipse “**.classpath**” for dependency purpose. Like it again :) , no need to find the library and copy it myself.

## 8\. Create Hibernate Mapping file + Model class

In prerequisite requirement , a table named “stock” is created, now you will need to create a Hibernate XML mapping file and model class for it.

Create a `Stock.hbm.xml` file and put it in “**src/main/resources/com/mkyong/common/Stock.hbm.xml**“. Create “resources/com/mkyong/common/” folder if it does not exists.

_File : Stock.hbm.xml_

    <?xml version="1.0"?>
    <!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

    <hibernate-mapping>
        <class name="com.mkyong.common.Stock" table="stock" catalog="mkyong">
            <id name="stockId" type="java.lang.Integer">
                <column name="STOCK_ID" />
                <generator class="identity" />
            </id>
            <property name="stockCode" type="string">
                <column name="STOCK_CODE" length="10" not-null="true" unique="true" />
            </property>
            <property name="stockName" type="string">
                <column name="STOCK_NAME" length="20" not-null="true" unique="true" />
            </property>
        </class>
    </hibernate-mapping>

Create a Stock.java file and put it in “**src/main/java/com/mkyong/common/Stock.java**”

_File : Stock.java_

    package com.mkyong.common;

    /**
     * Model class for Stock
     */
    public class Stock implements java.io.Serializable {

    	private static final long serialVersionUID = 1L;

    	private Integer stockId;
    	private String stockCode;
    	private String stockName;

    	public Stock() {
    	}

    	public Stock(String stockCode, String stockName) {
    		this.stockCode = stockCode;
    		this.stockName = stockName;
    	}

    	public Integer getStockId() {
    		return this.stockId;
    	}

    	public void setStockId(Integer stockId) {
    		this.stockId = stockId;
    	}

    	public String getStockCode() {
    		return this.stockCode;
    	}

    	public void setStockCode(String stockCode) {
    		this.stockCode = stockCode;
    	}

    	public String getStockName() {
    		return this.stockName;
    	}

    	public void setStockName(String stockName) {
    		this.stockName = stockName;
    	}

    }

**Note**  
Create the model class and mapping files are quite tedious in large application, With Hibernate tools, this can be generate automatically, check this article – [Hibernate tools to generate it automatically](http://www.mkyong.com/hibernate/how-to-generate-code-with-hibernate-tools/).

## 9\. Create Hibernate Configuration file

Create a Hibernate’s configuration file and put under the resources root folder, “**src/main/resources/hibernate.cfg.xml**“. Fill in your MySQL setting accordingly.

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
            <mapping resource="com/mkyong/common/Stock.hbm.xml"></mapping>
        </session-factory>
    </hibernate-configuration>

Set the “show_sql” property to true will output the Hibernate SQL statement. Hibernate Dialect is telling your Hibernate application which SQL it has to generate to talk to your database. Please refer this article for other database dialect – [Hibernate dialect collection](http://www.mkyong.com/hibernate/hibernate-dialect-collection/).

## 10\. Create Hibernate Utility class

Create a HibernateUtil.java class to take care of Hibernate start up and retrieve the session easily. Create a persistence folder and put this file in it, “**src/main/java/com/mkyong/persistence/HibernateUtil.java**”

_File : HibernateUtil.java_

    package com.mkyong.persistence;

    import org.hibernate.SessionFactory;
    import org.hibernate.cfg.Configuration;

    public class HibernateUtil {

        private static final SessionFactory sessionFactory = buildSessionFactory();

        private static SessionFactory buildSessionFactory() {
            try {
                // Create the SessionFactory from hibernate.cfg.xml
                return new Configuration().configure().buildSessionFactory();
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

## 11\. Review project structure again

Sound like create many new files and folders, review it and make sure the folder structure as following

![hibernate-example-2](http://www.mkyong.com/wp-content/uploads/2009/12/hibernate-example-2.jpg)

## 12\. Code it to save the record

Modify the default App.java class as following :

_File : App.java_

    package com.mkyong.common;

    import org.hibernate.Session;
    import com.mkyong.persistence.HibernateUtil;

    public class App
    {
        public static void main( String[] args )
        {
            System.out.println("Maven + Hibernate + MySQL");
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            Stock stock = new Stock();

            stock.setStockCode("4715");
            stock.setStockName("GENM");

            session.save(stock);
            session.getTransaction().commit();
        }
    }

## 13\. Run it and see output

Run your App.java, it will insert a new record into “Stock” table.

    Maven + Hibernate + MySQL
    ...
    Dec 17, 2009 5:05:26 PM org.hibernate.impl.SessionFactoryObjectFactory addInstance
    INFO: Not binding factory to JNDI, no JNDI name configured
    Hibernate: insert into mkyong.stock (STOCK_CODE, STOCK_NAME) values (?, ?)

Done.

[http://www.mkyong.com/hibernate/quick-start-maven-hibernate-mysql-example/](http://www.mkyong.com/hibernate/quick-start-maven-hibernate-mysql-example/)

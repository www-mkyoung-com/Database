> Many-to-many relationships occur when each record in an entity may have many linked records in another entity and vice-versa.

In this tutorial, we show you how to work with many-to-many table relationship in Hibernate, via XML mapping file (hbm).

**Note **  
For many to many with extra columns in join table, please refer to this [tutorial](http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/).

Tools and technologies used in this tutorials :

1.  Hibernate 3.6.3.Final
2.  MySQL 5.1.15
3.  Maven 3.0.3
4.  Eclipse 3.6

## Project Structure

Project structure of this tutorial.

![many to many project folder](http://www.mkyong.com/wp-content/uploads/2010/02/many-to-many-project-folder.png)

## Project Dependency

Get latest **hibernate.jar** from JBoss repository.

_File : pom.xml_

    <project ...>

    	<repositories>
    		<repository>
    			<id>JBoss repository</id>
    			<url>http://repository.jboss.org/nexus/content/groups/public/</url>
    		</repository>
    	</repositories>

    	<dependencies>

    		<!-- MySQL database driver -->
    		<dependency>
    			<groupId>mysql</groupId>
    			<artifactId>mysql-connector-java</artifactId>
    			<version>5.1.15</version>
    		</dependency>

    		<dependency>
    			<groupId>org.hibernate</groupId>
    			<artifactId>hibernate-core</artifactId>
    			<version>3.6.3.Final</version>
    		</dependency>

    		<dependency>
    			<groupId>javassist</groupId>
    			<artifactId>javassist</artifactId>
    			<version>3.12.1.GA</version>
    		</dependency>

    	</dependencies>
    </project>

## 1\. “Many-to-many” example

This is a many-to-many relationship table design, a STOCK table has more than one CATEGORY, and CATEGORY can belong to more than one STOCK, the relationship is linked with a third table called STOCK_CATEGORY.

Table STOCK_CATEGORY only consist of two primary keys, and also foreign key reference back to STOCK and CATEGORY.

![many to many ER diagram](http://www.mkyong.com/wp-content/uploads/2010/02/many-to-many-diagram.png)

_MySQL table scripts_

    CREATE TABLE `stock` (
      `STOCK_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `STOCK_CODE` varchar(10) NOT NULL,
      `STOCK_NAME` varchar(20) NOT NULL,
      PRIMARY KEY (`STOCK_ID`) USING BTREE,
      UNIQUE KEY `UNI_STOCK_NAME` (`STOCK_NAME`),
      UNIQUE KEY `UNI_STOCK_ID` (`STOCK_CODE`) USING BTREE
    )

    CREATE TABLE `category` (
      `CATEGORY_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `NAME` varchar(10) NOT NULL,
      `DESC` varchar(255) NOT NULL,
      PRIMARY KEY (`CATEGORY_ID`) USING BTREE
    )

    CREATE TABLE  `stock_category` (
      `STOCK_ID` int(10) unsigned NOT NULL,
      `CATEGORY_ID` int(10) unsigned NOT NULL,
      PRIMARY KEY (`STOCK_ID`,`CATEGORY_ID`),
      CONSTRAINT `FK_CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`CATEGORY_ID`),
      CONSTRAINT `FK_STOCK_ID` FOREIGN KEY (`STOCK_ID`) REFERENCES `stock` (`STOCK_ID`)
    )

## 2\. Hibernate Model Class

Create two model classes – `Stock.java` and `Category.java`, to represent the above tables. No need to create an extra class for table ‘**stock_category**‘.

_File : Stock.java_

    package com.mkyong.stock;

    import java.util.HashSet;
    import java.util.Set;

    public class Stock implements java.io.Serializable {

    	private Integer stockId;
    	private String stockCode;
    	private String stockName;
    	private Set<Category> categories = new HashSet<Category>(0);

    	//getter, setter and constructor
    }

_File : Category.java_

    package com.mkyong.stock;

    import java.util.HashSet;
    import java.util.Set;

    public class Category implements java.io.Serializable {

    	private Integer categoryId;
    	private String name;
    	private String desc;
    	private Set<Stock> stocks = new HashSet<Stock>(0);

    	//getter, setter and constructor
    }

## 3\. Hibernate XML Mapping

Now, create two Hibernate mapping files (hbm) – `Stock.hbm.xml` and `Category.hbm.xml`. You will noticed the third ‘**stock_category**‘ table is reference via “**many-to-many**” tag.

_File : Stock.hbm.xml_

    <?xml version="1.0"?>
    <!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
    <hibernate-mapping>
        <class name="com.mkyong.stock.Stock" table="stock" catalog="mkyongdb">
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
            <set name="categories" table="stock_category"
            	inverse="false" lazy="true" fetch="select" cascade="all" >
                <key>
                    <column name="STOCK_ID" not-null="true" />
                </key>
                <many-to-many entity-name="com.mkyong.stock.Category">
                    <column name="CATEGORY_ID" not-null="true" />
                </many-to-many>
            </set>
        </class>
    </hibernate-mapping>

_File : Category.hbm.xml_

    <?xml version="1.0"?>
    <!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
    <hibernate-mapping>
        <class name="com.mkyong.stock.Category" table="category" catalog="mkyongdb">
            <id name="categoryId" type="java.lang.Integer">
                <column name="CATEGORY_ID" />
                <generator class="identity" />
            </id>
            <property name="name" type="string">
                <column name="NAME" length="10" not-null="true" />
            </property>
            <property name="desc" type="string">
                <column name="[DESC]" not-null="true" />
            </property>
            <set name="stocks" table="stock_category" inverse="true" lazy="true" fetch="select">
                <key>
                    <column name="CATEGORY_ID" not-null="true" />
                </key>
                <many-to-many entity-name="com.mkyong.stock.Stock">
                    <column name="STOCK_ID" not-null="true" />
                </many-to-many>
            </set>
        </class>
    </hibernate-mapping>

## 4\. Hibernate Configuration File

Now, puts `Stock.hbm.xml` and `Category.hbm.xml` and MySQL detail in `hibernate.cfg.xml`.

_File : hibernate.cfg.xml_

    <?xml version="1.0" encoding="utf-8"?>
    <!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

    <hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/mkyongdb</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">password</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="show_sql">true</property>
        <property name="format_sql">true</property>
        <mapping resource="com/mkyong/stock/Stock.hbm.xml" />
        <mapping resource="com/mkyong/stock/Category.hbm.xml" />
    </session-factory>
    </hibernate-configuration>

## 5\. Run It

Run it, Hibernate will insert a record into the STOCK table, two records into the CATEGORY table, and also two records into the STOCK)CATEGORY table.

_File : App.java_

    package com.mkyong;

    import java.util.HashSet;
    import java.util.Set;
    import org.hibernate.Session;
    import com.mkyong.stock.Category;
    import com.mkyong.stock.Stock;
    import com.mkyong.util.HibernateUtil;

    public class App {
    	public static void main(String[] args) {

            System.out.println("Hibernate many to many (XML Mapping)");
    	Session session = HibernateUtil.getSessionFactory().openSession();

    	session.beginTransaction();

    	Stock stock = new Stock();
            stock.setStockCode("7052");
            stock.setStockName("PADINI");

            Category category1 = new Category("CONSUMER", "CONSUMER COMPANY");
            Category category2 = new Category("INVESTMENT", "INVESTMENT COMPANY");

            Set<Category> categories = new HashSet<Category>();
            categories.add(category1);
            categories.add(category2);

            stock.setCategories(categories);

            session.save(stock);

    	session.getTransaction().commit();
    	System.out.println("Done");
    	}
    }

_Output …result should be self-explanatory_

    Hibernate many to many (XML Mapping)
    Hibernate:
        insert
        into
            mkyongdb.stock
            (STOCK_CODE, STOCK_NAME)
        values
            (?, ?)
    Hibernate:
        insert
        into
            mkyongdb.category
            (NAME, `DESC`)
        values
            (?, ?)
    Hibernate:
        insert
        into
            mkyongdb.category
            (NAME, `DESC`)
        values
            (?, ?)
    Hibernate:
        insert
        into
            stock_category
            (STOCK_ID, CATEGORY_ID)
        values
            (?, ?)
    Hibernate:
        insert
        into
            stock_category
            (STOCK_ID, CATEGORY_ID)
        values
            (?, ?)
    Done

**Hibernate Annotation**  
For many-to-many in Hibernate annotation, please refer to this [example](http://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example-annotation/).

[http://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example/](http://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example/)

> A one-to-many relationship occurs when one entity is related to many occurrences in another entity.

In this tutorial, we show you how to works with one-to-many table relationship in Hibernate, via XML mapping file (hbm).

Tools and technologies used in this tutorials :

1.  Hibernate 3.6.3.Final
2.  MySQL 5.1.15
3.  Maven 3.0.3
4.  Eclipse 3.6

## Project Structure

Project structure of this tutorial.

![one to many folder](http://www.mkyong.com/wp-content/uploads/2010/02/one-to-many-relationship-folder.png)

## Project Dependency

Get **hibernate.jar** from JBoss repository, Maven will take care all the related dependencies for you

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

## 1\. “One-to-many” example

This is a **one-to-many** relationship table design, a STOCK table has many occurrences STOCK_DAILY_RECORD table.

![one to many table relationship](http://www.mkyong.com/wp-content/uploads/2010/02/one-to-many-relationship.png)

See MySQL table scripts

    DROP TABLE IF EXISTS `stock`;
    CREATE TABLE `stock` (
      `STOCK_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `STOCK_CODE` varchar(10) NOT NULL,
      `STOCK_NAME` varchar(20) NOT NULL,
      PRIMARY KEY (`STOCK_ID`) USING BTREE,
      UNIQUE KEY `UNI_STOCK_NAME` (`STOCK_NAME`),
      UNIQUE KEY `UNI_STOCK_ID` (`STOCK_CODE`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

    DROP TABLE IF EXISTS `mkyongdb`.`stock_daily_record`;
    CREATE TABLE  `mkyongdb`.`stock_daily_record` (
      `RECORD_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `PRICE_OPEN` float(6,2) DEFAULT NULL,
      `PRICE_CLOSE` float(6,2) DEFAULT NULL,
      `PRICE_CHANGE` float(6,2) DEFAULT NULL,
      `VOLUME` bigint(20) unsigned DEFAULT NULL,
      `DATE` date NOT NULL,
      `STOCK_ID` int(10) unsigned NOT NULL,
      PRIMARY KEY (`RECORD_ID`) USING BTREE,
      UNIQUE KEY `UNI_STOCK_DAILY_DATE` (`DATE`),
      KEY `FK_STOCK_TRANSACTION_STOCK_ID` (`STOCK_ID`),
      CONSTRAINT `FK_STOCK_TRANSACTION_STOCK_ID` FOREIGN KEY (`STOCK_ID`)
      REFERENCES `stock` (`STOCK_ID`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

## 2\. Hibernate Model Class

Create two model classes – `Stock.java` and `StockDailyRecord.java`, to represent the above tables.

_File : Stock.java_

    package com.mkyong.stock;

    import java.util.HashSet;
    import java.util.Set;

    public class Stock implements java.io.Serializable {

    	private Integer stockId;
    	private String stockCode;
    	private String stockName;
    	private Set<StockDailyRecord> stockDailyRecords =
    				new HashSet<StockDailyRecord>(0);

    	//getter, setter and constructor
    }

_File : StockDailyRecord.java_

    package com.mkyong.stock;

    import java.util.Date;

    public class StockDailyRecord implements java.io.Serializable {

    	private Integer recordId;
    	private Stock stock;
    	private Float priceOpen;
    	private Float priceClose;
    	private Float priceChange;
    	private Long volume;
    	private Date date;

    	//getter, setter and constructor
    }

## 3\. Hibernate XML Mapping

Now, create two Hibernate mapping files (hbm) – `Stock.hbm.xml` and `StockDailyRecord.hbm.xml`.

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
            <set name="stockDailyRecords" table="stock_daily_record"
    				inverse="true" lazy="true" fetch="select">
                <key>
                    <column name="STOCK_ID" not-null="true" />
                </key>
                <one-to-many class="com.mkyong.stock.StockDailyRecord" />
            </set>
        </class>
    </hibernate-mapping>

_File : StockDailyRecord.hbm.xml_

    <?xml version="1.0"?>
    <!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
    <hibernate-mapping>
        <class name="com.mkyong.stock.StockDailyRecord" table="stock_daily_record"
    		catalog="mkyongdb">
            <id name="recordId" type="java.lang.Integer">
                <column name="RECORD_ID" />
                <generator class="identity" />
            </id>
            <many-to-one name="stock" class="com.mkyong.stock.Stock" fetch="select">
                <column name="STOCK_ID" not-null="true" />
            </many-to-one>
            <property name="priceOpen" type="java.lang.Float">
                <column name="PRICE_OPEN" precision="6" />
            </property>
            <property name="priceClose" type="java.lang.Float">
                <column name="PRICE_CLOSE" precision="6" />
            </property>
            <property name="priceChange" type="java.lang.Float">
                <column name="PRICE_CHANGE" precision="6" />
            </property>
            <property name="volume" type="java.lang.Long">
                <column name="VOLUME" />
            </property>
            <property name="date" type="date">
                <column name="DATE" length="10" not-null="true" unique="true" />
            </property>
        </class>
    </hibernate-mapping>

## 4\. Hibernate Configuration File

Puts `Stock.hbm.xml` and `StockDailyRecord.hbm.xml` in your Hibernate configuration file, and also MySQL connection details.

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
        <mapping resource="com/mkyong/stock/StockDailyRecord.hbm.xml" />
    </session-factory>
    </hibernate-configuration>

## 5\. Run It

Run it, Hibernate will insert a row into the STOCK table and a row into the STOCK_DAILY_RECORD table.

_File : App.java_

    package com.mkyong;

    import java.util.Date;

    import org.hibernate.Session;

    import com.mkyong.stock.Stock;
    import com.mkyong.stock.StockDailyRecord;
    import com.mkyong.util.HibernateUtil;

    public class App {
    	public static void main(String[] args) {

            System.out.println("Hibernate one to many (XML Mapping)");
    	Session session = HibernateUtil.getSessionFactory().openSession();

    	session.beginTransaction();

    	Stock stock = new Stock();
            stock.setStockCode("7052");
            stock.setStockName("PADINI");
            session.save(stock);

            StockDailyRecord stockDailyRecords = new StockDailyRecord();
            stockDailyRecords.setPriceOpen(new Float("1.2"));
            stockDailyRecords.setPriceClose(new Float("1.1"));
            stockDailyRecords.setPriceChange(new Float("10.0"));
            stockDailyRecords.setVolume(3000000L);
            stockDailyRecords.setDate(new Date());

            stockDailyRecords.setStock(stock);
            stock.getStockDailyRecords().add(stockDailyRecords);

            session.save(stockDailyRecords);

    	session.getTransaction().commit();
    	System.out.println("Done");
    	}
    }

_Output …_

    Hibernate one to many (XML Mapping)
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
            mkyongdb.stock_daily_record
            (STOCK_ID, PRICE_OPEN, PRICE_CLOSE, PRICE_CHANGE, VOLUME, DATE)
        values
            (?, ?, ?, ?, ?, ?)

**Hibernate Annotation**  
For one-to-many in Hibernate annotation, please refer to this [example](http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example-annotation/).

[http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example/](http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example/)

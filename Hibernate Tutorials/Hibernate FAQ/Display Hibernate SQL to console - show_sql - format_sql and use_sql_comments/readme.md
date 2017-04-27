Hibernate has build-in a function to enable the logging of all the generated SQL statements to the console. You can enable it by add a “**show_sql**” property in the Hibernate configuration file “`hibernate.cfg.xml`“. This function is good for basic troubleshooting, and to see what’s Hibernate is doing behind.

## 1\. show_sql

Enable the logging of all the generated SQL statements to the console

    <!--hibernate.cfg.xml -->
    <property name="show_sql">true</property>

Output

    Hibernate: insert into mkyong.stock_transaction
    (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
    values (?, ?, ?, ?, ?, ?)

## 2\. format_sql

Format the generated SQL statement to make it more readable, but takes up more screen space. :)

    <!--hibernate.cfg.xml -->
    <property name="format_sql">true</property>

Output

    Hibernate:
        insert
        into
            mkyong.stock_transaction
            (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
        values
            (?, ?, ?, ?, ?, ?)

## 3\. use_sql_comments

Hibernate will put comments inside all generated SQL statements to hint what’s the generated SQL trying to do

    <!--hibernate.cfg.xml -->
    <property name="use_sql_comments">true</property>

Output

    Hibernate:
        /* insert com.mkyong.common.StockTransaction
            */ insert
            into
                mkyong.stock_transaction
                (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
            values
                (?, ?, ?, ?, ?, ?)

## Hibernate configuration file

Full example of “`hibernate.cfg.xml`“.

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
            <property name="format_sql">true</property>
            <property name="use_sql_comments">true</property>
        </session-factory>
    </hibernate-configuration>

## How about Hibernate SQL parameter value?

This basic SQL logging is good enough for the normal debugging, however it’s unable to display the Hibernate SQL parameter value. Some third party libraries integration are required to display the Hibernate SQL parameter value to console or file. Check the following two articles :

1.  [How to display hibernate sql parameter values – P6Spy](http://www.mkyong.com/hibernate/how-to-display-hibernate-sql-parameter-values-solution/)
2.  [How to display hibernate sql parameter values – Log4J](http://www.mkyong.com/hibernate/how-to-display-hibernate-sql-parameter-values-log4j/)

[http://www.mkyong.com/hibernate/hibernate-display-generated-sql-to-console-show_sql-format_sql-and-use_sql_comments/](http://www.mkyong.com/hibernate/hibernate-display-generated-sql-to-console-show_sql-format_sql-and-use_sql_comments/)

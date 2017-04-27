## Question

There are many developers asking about Hibernate SQL parameter value question. How to display the Hibernate SQL parameter values that passed to database? Hibernate just display all parameter values as question mark (?). With [show_sql](http://www.mkyong.com/hibernate/hibernate-display-generated-sql-to-console-show_sql-format_sql-and-use_sql_comments/) property, Hibernate will shows all generated SQL statements, but not the SQL parameter values.

_For example_

    Hibernate: insert into mkyong.stock_transaction (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
    values (?, ?, ?, ?, ?, ?)

Is there a way to log or display the exact Hibernate SQL parameter values?

## Solution – P6Spy

Well, if there is a question there is an answer ~

The P6Spy is a useful library to log all SQL statement and parameter values before send it to database. The P6Spy is free, it’s use to intercepts and logs all your database SQL statements into a log file, and it works for any application that uses JDBC driver.

## 1\. Download P6Spy library

Get the “**p6spy-install.jar**“, you can download it from

1.  [P6Spy official website](http://www.p6spy.com/).
2.  [P6Spy at Sourceforge.net](http://sourceforge.net/projects/p6spy/)

## 2\. Extract it

Extract the p6spy-install.jar file, look for **p6spy.jar** and **spy.properties**

## 3\. Add library dependency

Add **p6spy.jar** into your project library dependency

## 4\. Modify P6Spy properties file

Modify your database configuration file. You need to replace your existing JDBC driver with P6Spy JDBC driver – ” `com.p6spy.engine.spy.P6SpyDriver`”

_Original is MySQL JDBC driver – “com.mysql.jdbc.Driver”_

    <session-factory>
      <property name="hibernate.bytecode.use_reflection_optimizer">false</property>
      <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
      <property name="hibernate.connection.password">password</property>
      <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/mkyong</property>
      <property name="hibernate.connection.username">root</property>
      <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
      <property name="show_sql">true</property>
    </session-factory>

_Changed it to P6Spy JDBC driver – “com.p6spy.engine.spy.P6SpyDriver”_

    <session-factory>
      <property name="hibernate.bytecode.use_reflection_optimizer">false</property>
      <property name="hibernate.connection.driver_class">com.p6spy.engine.spy.P6SpyDriver
      </property>
      <property name="hibernate.connection.password">password</property>
      <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/mkyong</property>
      <property name="hibernate.connection.username">root</property>
      <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
      <property name="show_sql">true</property>
    </session-factory>

## 5\. Modify P6Spy properties file

Modify the P6Spy properties file – “**spy.properties**”

Replace the “real driver” with your existing MySQL JDBC driver

    realdriver=com.mysql.jdbc.Driver

    #specifies another driver to use
    realdriver2=
    #specifies a third driver to use
    realdriver3=

**Change the Log file location**  
Change the log file location in **logfile** property, all SQL statements will log into this file.

**Windows**

    logfile     = c:/spy.log

***nix**

    logfile     = /srv/log/spy.log

## 6\. Copy “spy.properties” to project classpath

Copy “spy.properties” to your project root folder, make sure your project can locate “spy.properties”, else it will prompt “spy.properties” file not found exception.

## 7\. Done

Run your application and do some database transaction, you will notice all the SQL statements sent from application to database will be logged into a file you specified in “spy.properties”.

Sample log file as following.

    insert into mkyong.stock_transaction (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
    values (?, ?, ?, ?, ?, ?)|
    insert into mkyong.stock_transaction (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
    values (10.0, 1.1, '2009-12-30', 1.2, 11, 1000000)

## Conclusion

Frankly, the P6Spy is really useful in reducing the developers’ debugging time. As long as your project is using JDBC driver for connection , P6Sqp can fir into it and log all SQL statements and parameter values for you.

## For Maven User

You can use Maven to download the P6Spy dependency into your `pom.xml`

    <dependency>
    	<groupId>p6spy</groupId>
    	<artifactId>p6spy</artifactId>
    	<version>1.3</version>
    </dependency>

However the “spy.properties” file is not come in package, you have to create it yourself. You can download the template here – [spy.properties](http://www.mkyong.com/wp-content/uploads/2008/12/spy.properties.zip)

## Reference

1.  [P6Spy configuration](http://www.p6spy.com/documentation/install.htm)

[http://www.mkyong.com/hibernate/how-to-display-hibernate-sql-parameter-values-solution/](http://www.mkyong.com/hibernate/how-to-display-hibernate-sql-parameter-values-solution/)

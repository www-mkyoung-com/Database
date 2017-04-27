## Problem

Hibernate has basic logging feature to display the SQL generated statement with [show_sql](http://www.mkyong.com/hibernate/hibernate-display-generated-sql-to-console-show_sql-format_sql-and-use_sql_comments/) configuration property.

    Hibernate: INSERT INTO mkyong.stock_transaction (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
    VALUES (?, ?, ?, ?, ?, ?)

However , it just isn’t enough for debugging, the Hibernate SQL parameter values are missing.

## Solution – Log4j

Log4J is required to display the real Hibernate SQL parameter value.

## 1\. Configure the Log4j in Hibernate

Follow this article to [configure Log4j in Hibernate](http://www.mkyong.com/hibernate/how-to-configure-log4j-in-hibernate-project/)

## 2\. Change the Log level

Modify the Log4j properties file, and change the log level to “debug” or “trace” in “**log4j.logger.org.hibernate.type**” property.

_File : log4j.properties_

    # Direct log messages to stdout
    log4j.appender.stdout=org.apache.log4j.ConsoleAppender
    log4j.appender.stdout.Target=System.out
    log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
    log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n

    # Root logger option
    log4j.rootLogger=INFO, stdout

    # Hibernate logging options (INFO only shows startup messages)
    log4j.logger.org.hibernate=INFO

    # Log JDBC bind parameter runtime arguments
    log4j.logger.org.hibernate.type=trace

## 3\. Done

The Hibernate real parameter values are display now

_Output…_

    Hibernate: INSERT INTO mkyong.stock_transaction (CHANGE, CLOSE, DATE, OPEN, STOCK_ID, VOLUME)
    VALUES (?, ?, ?, ?, ?, ?)
    13:33:07,253 DEBUG FloatType:133 - binding '10.0' to parameter: 1
    13:33:07,253 DEBUG FloatType:133 - binding '1.1' to parameter: 2
    13:33:07,253 DEBUG DateType:133 - binding '30 December 2009' to parameter: 3
    13:33:07,269 DEBUG FloatType:133 - binding '1.2' to parameter: 4
    13:33:07,269 DEBUG IntegerType:133 - binding '11' to parameter: 5
    13:33:07,269 DEBUG LongType:133 - binding '1000000' to parameter: 6

**Note**  
If this logging is still not detail enough for you to debug the SQL problem, you can use the P6Spy library to log the **exact SQL statement** that send to database. Check this article – [How to display hibernate sql parameter values with P6Spy](http://www.mkyong.com/hibernate/how-to-display-hibernate-sql-parameter-values-solution/)

[http://www.mkyong.com/hibernate/how-to-display-hibernate-sql-parameter-values-log4j/](http://www.mkyong.com/hibernate/how-to-display-hibernate-sql-parameter-values-log4j/)

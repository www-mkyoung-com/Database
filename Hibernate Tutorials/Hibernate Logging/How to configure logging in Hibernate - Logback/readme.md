In this tutorial, we show how to integrate [Logback logging framework](http://logback.qos.ch/) with Hibernate.

Tools and Technologies used in this tutorial :

1.  Hibernate 3.6.3.Final
2.  slf4j-api-1.6.1
3.  logback-core-0.9.28
4.  logback-classic-0.9.28
5.  Eclipse 3.6
6.  Maven 3.0.3

## 1\. Get SLF4j + Logback

To use logback in Hibernate web application, you need 3 libraries :

1.  slf4j-api.jar
2.  logback-core
3.  logback-classic

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

    		<!-- logback -->
    		<dependency>
    			<groupId>ch.qos.logback</groupId>
    			<artifactId>logback-core</artifactId>
    			<version>0.9.28</version>
    		</dependency>

    		<dependency>
    			<groupId>ch.qos.logback</groupId>
    			<artifactId>logback-classic</artifactId>
    			<version>0.9.28</version>
    		</dependency>

    	</dependencies>
    </project>

**Where is slf4j-api.jar?**  
The **slf4j-api.jar** is defined as the dependency of “**hibernate-core**“, so , you do not need to declare it again.

## 2\. logback.xml

Create a “**logback.xml**” file and put it into your project’s classpath, see figure below :

![logback hibernate](http://www.mkyong.com/wp-content/uploads/2011/04/configure-logback-hibernate.png)

A classic “`logback.xml`” example.

    <?xml version="1.0" encoding="UTF-8"?>
    <configuration>

     <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    	<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
    		<Pattern>%d{yyyy-MM-dd_HH:mm:ss.SSS} %-5level %logger{36} - %msg%n
                    </Pattern>
    	</encoder>
     </appender>

     <appender name="FILE"
    	class="ch.qos.logback.core.rolling.RollingFileAppender">
    	<file>c:/mkyongapp.log</file>
    	<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
    	   <Pattern>%d{yyyy-MM-dd_HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
               </Pattern>
    	</encoder>

    	<rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
    		<FileNamePattern>c:/mkyongapp.%i.log.zip</FileNamePattern>
    		<MinIndex>1</MinIndex>
    		<MaxIndex>10</MaxIndex>
    	</rollingPolicy>

    	<triggeringPolicy
    		class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
    		<MaxFileSize>2MB</MaxFileSize>
    	</triggeringPolicy>

      </appender>

      <logger name="org.hibernate.type" level="ALL" />
      <logger name="org.hibernate" level="DEBUG" />

      <root level="INFO">
    	<appender-ref ref="FILE" />
    	<appender-ref ref="STDOUT" />
      </root>

    </configuration>

With this `logback.xml` configuration, it means, redirect all your web application logging outputs to console and also a file at “`c:/mkyongapp.log`“.

## 3\. Output

See logback logging output at “`C:\\mkyongapp.log`” below :

    //...
    2011-04-23_14:34:08.055 [main] DEBUG o.h.transaction.JDBCTransaction - commit
    2011-04-23_14:34:08.056 [main] DEBUG o.h.e.d.AbstractFlushingEventListener - processing flush-time cascades
    2011-04-23_14:34:08.056 [main] DEBUG o.h.e.d.AbstractFlushingEventListener - dirty checking collections
    2011-04-23_14:34:08.058 [main] DEBUG o.h.e.d.AbstractFlushingEventListener - Flushed: 1 insertions, 0 updates, 0 deletions to 1 objects
    2011-04-23_14:34:08.058 [main] DEBUG o.h.e.d.AbstractFlushingEventListener - Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
    2011-04-23_14:34:08.059 [main] DEBUG org.hibernate.pretty.Printer - listing entities:
    2011-04-23_14:34:08.060 [main] DEBUG org.hibernate.pretty.Printer - com.mkyong.user.DBUser{username=Hibernate101, createdBy=system, userId=100, createdDate=Sat Apr 23 14:34:08 SGT 2011}
    2011-04-23_14:34:08.064 [main] DEBUG org.hibernate.jdbc.AbstractBatcher - about to open PreparedStatement (open PreparedStatements: 0, globally: 0)
    2011-04-23_14:34:08.066 [main] DEBUG org.hibernate.SQL - insert into MKYONG.DBUSER (CREATED_BY, CREATED_DATE, USERNAME, USER_ID) values (?, ?, ?, ?)
    2011-04-23_14:34:08.150 [main] TRACE o.h.type.descriptor.sql.BasicBinder - binding parameter [1] as [VARCHAR] - system
    2011-04-23_14:34:08.152 [main] TRACE o.h.type.descriptor.sql.BasicBinder - binding parameter [2] as [DATE] - Sat Apr 23 14:34:08 SGT 2011
    2011-04-23_14:34:08.153 [main] TRACE o.h.type.descriptor.sql.BasicBinder - binding parameter [3] as [VARCHAR] - Hibernate101
    2011-04-23_14:34:08.153 [main] TRACE o.h.type.descriptor.sql.BasicBinder - binding parameter [4] as [INTEGER] - 100

[http://www.mkyong.com/hibernate/how-to-configure-logging-in-hibernate-logback/](http://www.mkyong.com/hibernate/how-to-configure-logging-in-hibernate-logback/)

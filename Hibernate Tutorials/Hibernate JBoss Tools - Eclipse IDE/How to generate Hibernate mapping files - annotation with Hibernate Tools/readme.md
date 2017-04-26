In this article, we show you how to use **Hibernate / JBoss Tools** to generate Hibernate mapping files (hbm) and annotation code from database automatically.

Tools in this article

1.  Eclipse v3.6 (Helios)
2.  JBoss / Hibernate Tools v3.2
3.  Oracle 11g
4.  JDK 1.6

**Note**  
Before proceed, please [Install Hibernate / JBoss Tools in Eclipse IDE](http://www.mkyong.com/hibernate/how-to-install-hibernate-tools-in-eclipse-ide/).

## 1\. Hibernate Perspective

Open your “**Hibernate Perspective**“. In Eclipse IDE, select “**Windows**” >> “**Open Perspective**” >> “**Others…**” , choose “**Hibernate**“.

## 2\. New Hibernate Configuration

In Hibernate Perspective, right click and select “**Add Configuration…**”

In “Edit Configuration” dialog box,

1.  In “**Project**” box, click on the “Browse..” button to select your project.
2.  In “**Database Connection**” box, click “New..” button to create your database settings.
3.  In “**Configuration File**” box, click “Setup” button to create a new or use existing “Hibernate configuration file”, `hibernate.cfg.xml`.

![Eclipse Hibernate Tools](http://www.mkyong.com/wp-content/uploads/2009/12/Eclipse-Hibernate-1.png)

See your list of your tables in “**Hibernate Perspective**“.

![Eclipse Hibernate Tools](http://www.mkyong.com/wp-content/uploads/2009/12/Eclipse-Hibernate-2.png)

Sample of “`hibernate.cfg.xml`“, connect to Oracle 11g database.

    <?xml version="1.0" encoding="utf-8"?>
    <!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
    <hibernate-configuration>
    <session-factory>
      <property name="hibernate.connection.driver_class">oracle.jdbc.driver.OracleDriver</property>
      <property name="hibernate.connection.url">jdbc:oracle:thin:@127.0.0.1:1521:MKYONG</property>
      <property name="hibernate.connection.username">mkyong</property>
      <property name="hibernate.connection.password">password</property>
      <property name="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</property>
      <property name="hibernate.default_schema">MKYONG</property>
     </session-factory>
    </hibernate-configuration>

## 3\. Hibernate Code Generation

Now, you are ready to generate the Hibernate mapping files and annotation codes.

– In “Hibernate Perspective”, click “**Hibernate code generation**” icon (see below figure) and select “Hibernate Code Generation Configuration”

![Hibernate Code Generation](http://www.mkyong.com/wp-content/uploads/2009/12/Hibernate-Code-Generation-1.png)

– Create a new configuration, select your “**console configuration**” (configured in step 2), puts your “**Output directory**” and checked option “**Reverse engineer from JDBC Connection**“.

![Hibernate Code Generation](http://www.mkyong.com/wp-content/uploads/2009/12/Hibernate-Code-Generation-2.png)

– In “**Exporter**” tab, select what you want to generate, Model , mapping file (hbm) , DAO, annotation code and etc.

![Hibernate Code Generation](http://www.mkyong.com/wp-content/uploads/2009/12/Hibernate-Code-Generation-3.png)

See result

![Hibernate Code Generation](http://www.mkyong.com/wp-content/uploads/2009/12/Hibernate-Code-Generation-4.png)

**Note**  
The generated Hibernate mapping file and annotations code are very clean, standard and easy to modify. Try explore more features.

[http://www.mkyong.com/hibernate/how-to-generate-code-with-hibernate-tools/](http://www.mkyong.com/hibernate/how-to-generate-code-with-hibernate-tools/)

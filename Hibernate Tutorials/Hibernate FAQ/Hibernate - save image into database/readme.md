Here’s an Maven project to use Hibernate to save an image into MySQL ‘**avatar**‘ table.

## 1\. Table creation

An avatar table creation script in MySQL.

    CREATE TABLE  `mkyong`.`avatar` (
      `AVATAR_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
      `IMAGE` blob NOT NULL,
      PRIMARY KEY (`AVATAR_ID`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

## 2\. Maven dependency

Add Hibernate and MySQL dependency.

**pom.xml**

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

      </dependencies>
    </project>

## 3\. Avatar Model

Create a model class to store the avatar data. The image data type is array of bytes.

**Avatar.java**

    package com.mkyong.common;

    public class Avatar implements java.io.Serializable {

    	private Integer avatarId;
    	private byte[] image;

    	public Avatar() {
    	}

    	public Avatar(byte[] image) {
    		this.image = image;
    	}

    	public Integer getAvatarId() {
    		return this.avatarId;
    	}

    	public void setAvatarId(Integer avatarId) {
    		this.avatarId = avatarId;
    	}

    	public byte[] getImage() {
    		return this.image;
    	}

    	public void setImage(byte[] image) {
    		this.image = image;
    	}

    }

## 4\. Mapping file

Create a Hibernate mapping file for avatar. The data type for image is binary.

**Avatar.hbm.xml**

    <?xml version="1.0"?>
    <!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

    <hibernate-mapping>
        <class name="com.mkyong.common.Avatar" table="avatar" catalog="mkyong">
            <id name="avatarId" type="java.lang.Integer">
                <column name="AVATAR_ID" />
                <generator class="identity" />
            </id>
            <property name="image" type="binary">
                <column name="IMAGE" not-null="true" />
            </property>
        </class>
    </hibernate-mapping>

## 5\. Hibernate configuration file

Hibernate configuration file to define the database connection and Hibernate mapping file.

**hibernate.cfg.xml**

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
            <mapping resource="com/mkyong/common/Avatar.hbm.xml"></mapping>
        </session-factory>
    </hibernate-configuration>

## 6\. Hibernate utility

A Hibernate utility class to get the database connection.

**HibernateUtil.java**

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

## 7\. Run it

Read a file “**C:\\mavan-hibernate-image-mysql.gif**” and save it into database, later get it from database and save it into another image file “**C:\\test.gif**“.

    package com.mkyong.common;

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;

    import org.hibernate.Session;
    import com.mkyong.persistence.HibernateUtil;

    public class App
    {
        public static void main( String[] args )
        {
            System.out.println("Hibernate save image into database");
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            //save image into database
        	File file = new File("C:\\mavan-hibernate-image-mysql.gif");
            byte[] bFile = new byte[(int) file.length()];

            try {
    	     FileInputStream fileInputStream = new FileInputStream(file);
    	     //convert file into array of bytes
    	     fileInputStream.read(bFile);
    	     fileInputStream.close();
            } catch (Exception e) {
    	     e.printStackTrace();
            }

            Avatar avatar = new Avatar();
            avatar.setImage(bFile);

            session.save(avatar);

            //Get image from database
            Avatar avatar2 = (Avatar)session.get(Avatar.class, avatar.getAvatarId());
            byte[] bAvatar = avatar2.getImage();

            try{
                FileOutputStream fos = new FileOutputStream("C:\\test.gif");
                fos.write(bAvatar);
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }

            session.getTransaction().commit();
        }
    }

Done.

[http://www.mkyong.com/hibernate/hibernate-save-image-into-database/](http://www.mkyong.com/hibernate/hibernate-save-image-into-database/)

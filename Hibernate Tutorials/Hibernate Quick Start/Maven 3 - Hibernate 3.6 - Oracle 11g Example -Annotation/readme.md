This tutorial will reuse and modify the previous [Hibernate3.6 XML mapping tutorial](http://www.mkyong.com/hibernate/maven-3-hibernate-3-6-oracle-11g-example-xml-mapping/), but replace the Hibernate mapping file (hbm) with Hibernate / JPA Annotation code.

Technologies in this article :

1.  Maven 3.0.3
2.  JDK 1.6.0_13
3.  Hibernate 3.6.3.final
4.  Oracle 11g

## 1\. pom.xml

No change in `pom.xml` file, all previous [Hibernate3.6 XML mapping tutorial](http://www.mkyong.com/hibernate/maven-3-hibernate-3-6-oracle-11g-example-xml-mapping/) dependency can be reused.

**Note**  
Since Hibernate 3.6, the annotation is integrated into the `hibernate-core.jar` module. In previous version, for example, Hibernate 3.2, you need to include extra `hibernate-annotations.jar` to make it works.

## 2\. Delete Hibernate Mapping file (hbm)

Delete the “`DBUser.hbm.xml`” file, it’s no longer require.

## 3\. Update Model

Update “`DBUser.java`“, puts JPA annotation code inside.

_File : DBUser.java_

    package com.mkyong.user;

    import java.util.Date;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.Id;
    import javax.persistence.Table;
    import javax.persistence.Temporal;
    import javax.persistence.TemporalType;

    @Entity
    @Table(name = "DBUSER")
    public class DBUser implements java.io.Serializable {

    	private int userId;
    	private String username;
    	private String createdBy;
    	private Date createdDate;

    	public DBUser() {
    	}

    	public DBUser(int userId, String username, String createdBy,
    			Date createdDate) {
    		this.userId = userId;
    		this.username = username;
    		this.createdBy = createdBy;
    		this.createdDate = createdDate;
    	}

    	@Id
    	@Column(name = "USER_ID", unique = true, nullable = false, precision = 5, scale = 0)
    	public int getUserId() {
    		return this.userId;
    	}

    	public void setUserId(int userId) {
    		this.userId = userId;
    	}

    	@Column(name = "USERNAME", nullable = false, length = 20)
    	public String getUsername() {
    		return this.username;
    	}

    	public void setUsername(String username) {
    		this.username = username;
    	}

    	@Column(name = "CREATED_BY", nullable = false, length = 20)
    	public String getCreatedBy() {
    		return this.createdBy;
    	}

    	public void setCreatedBy(String createdBy) {
    		this.createdBy = createdBy;
    	}

    	@Temporal(TemporalType.DATE)
    	@Column(name = "CREATED_DATE", nullable = false, length = 7)
    	public Date getCreatedDate() {
    		return this.createdDate;
    	}

    	public void setCreatedDate(Date createdDate) {
    		this.createdDate = createdDate;
    	}

    }

## 4\. Update Hibernate Configuration File

Update “`hibernate.cfg.xml`“, replace the “**mapping resource**” with “**mapping class**”

_Update hibernate.cfg.xml, from this :_

    <hibernate-configuration>
      <session-factory>
        <!-- ..... -->
        <mapping resource="com/mkyong/user/DBUser.hbm.xml"></mapping>
      </session-factory>
    </hibernate-configuration>

_To this :_

    <hibernate-configuration>
      <session-factory>
        <!-- ..... -->
        <mapping class="com.mkyong.user.DBUser"></mapping>
      </session-factory>
    </hibernate-configuration>

## 5\. Hibernate Utility

No update on “`HibernateUtil.java`“, since Hibernate 3.6, both XML mapping and annotation are sharing the same “**org.hibernate.cfg.Configuration**” class.

**Bye bye AnnotationConfiguration**  
Read this – [AnnotationConfiguration is deprecated in Hibernate 3.6](http://www.mkyong.com/hibernate/hibernate-the-type-annotationconfiguration-is-deprecated/)

## 6\. Review Final Project Structure

Review your project structure :

![folder structure](http://www.mkyong.com/wp-content/uploads/2011/04/maven-hibernate-annotation-folder-structure.png)

## 7\. Run It

No update on “`App.java`“, as well, just run it, and you should be seeing the same result as previous [Hibernate3.6 XML mapping tutorial](http://www.mkyong.com/hibernate/maven-3-hibernate-3-6-oracle-11g-example-xml-mapping/).

[http://www.mkyong.com/hibernate/maven-3-hibernate-3-6-oracle-11g-example-annotation/](http://www.mkyong.com/hibernate/maven-3-hibernate-3-6-oracle-11g-example-annotation/)

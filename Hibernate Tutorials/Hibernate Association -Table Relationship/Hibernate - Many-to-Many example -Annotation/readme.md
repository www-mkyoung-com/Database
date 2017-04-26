In this tutorial, it will reuse the entire infrastructure of the previous “[Hibernate many to many example – XML mapping](http://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example/)” tutorial, enhance it to support Hibernare / JPA annotation.

**Note **  
For many to many with extra columns in join table, please refer to this [tutorial](http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/).

## Project Structure

Review the new project structure of this tutorial.

![many to many project folder](http://www.mkyong.com/wp-content/uploads/2011/04/many-to-many-annotation-project-folder.png)

## 1\. “Many-to-many” table relationship

See the previous many to many table relationship again.

![many to many ER diagram](http://www.mkyong.com/wp-content/uploads/2010/02/many-to-many-diagram.png)

## 2\. Hibernate Model Class

Update previous model classes – `Stock.java` and `Category.java`, and define new annotation code inside.

_File : Stock.java_

    package com.mkyong.stock;

    import java.util.HashSet;
    import java.util.Set;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.FetchType;
    import javax.persistence.GeneratedValue;
    import static javax.persistence.GenerationType.IDENTITY;
    import javax.persistence.CascadeType;
    import javax.persistence.Id;
    import javax.persistence.JoinColumn;
    import javax.persistence.JoinTable;
    import javax.persistence.ManyToMany;
    import javax.persistence.Table;
    import javax.persistence.UniqueConstraint;

    @Entity
    @Table(name = "stock", catalog = "mkyongdb", uniqueConstraints = {
    		@UniqueConstraint(columnNames = "STOCK_NAME"),
    		@UniqueConstraint(columnNames = "STOCK_CODE") })
    public class Stock implements java.io.Serializable {

    	private Integer stockId;
    	private String stockCode;
    	private String stockName;
    	private Set<Category> categories = new HashSet<Category>(0);

    	public Stock() {
    	}

    	public Stock(String stockCode, String stockName) {
    		this.stockCode = stockCode;
    		this.stockName = stockName;
    	}

    	public Stock(String stockCode, String stockName, Set<Category> categories) {
    		this.stockCode = stockCode;
    		this.stockName = stockName;
    		this.categories = categories;
    	}

    	@Id
    	@GeneratedValue(strategy = IDENTITY)
    	@Column(name = "STOCK_ID", unique = true, nullable = false)
    	public Integer getStockId() {
    		return this.stockId;
    	}

    	public void setStockId(Integer stockId) {
    		this.stockId = stockId;
    	}

    	@Column(name = "STOCK_CODE", unique = true, nullable = false, length = 10)
    	public String getStockCode() {
    		return this.stockCode;
    	}

    	public void setStockCode(String stockCode) {
    		this.stockCode = stockCode;
    	}

    	@Column(name = "STOCK_NAME", unique = true, nullable = false, length = 20)
    	public String getStockName() {
    		return this.stockName;
    	}

    	public void setStockName(String stockName) {
    		this.stockName = stockName;
    	}

    	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    	@JoinTable(name = "stock_category", catalog = "mkyongdb", joinColumns = {
    			@JoinColumn(name = "STOCK_ID", nullable = false, updatable = false) },
    			inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID",
    					nullable = false, updatable = false) })
    	public Set<Category> getCategories() {
    		return this.categories;
    	}

    	public void setCategories(Set<Category> categories) {
    		this.categories = categories;
    	}

    }

_File : Category.java_

    package com.mkyong.stock;

    import java.util.HashSet;
    import java.util.Set;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.FetchType;
    import javax.persistence.GeneratedValue;
    import static javax.persistence.GenerationType.IDENTITY;
    import javax.persistence.Id;
    import javax.persistence.ManyToMany;
    import javax.persistence.Table;

    @Entity
    @Table(name = "category", catalog = "mkyongdb")
    public class Category implements java.io.Serializable {

    	private Integer categoryId;
    	private String name;
    	private String desc;
    	private Set<Stock> stocks = new HashSet<Stock>(0);

    	public Category() {
    	}

    	public Category(String name, String desc) {
    		this.name = name;
    		this.desc = desc;
    	}

    	public Category(String name, String desc, Set<Stock> stocks) {
    		this.name = name;
    		this.desc = desc;
    		this.stocks = stocks;
    	}

    	@Id
    	@GeneratedValue(strategy = IDENTITY)
    	@Column(name = "CATEGORY_ID", unique = true, nullable = false)
    	public Integer getCategoryId() {
    		return this.categoryId;
    	}

    	public void setCategoryId(Integer categoryId) {
    		this.categoryId = categoryId;
    	}

    	@Column(name = "NAME", nullable = false, length = 10)
    	public String getName() {
    		return this.name;
    	}

    	public void setName(String name) {
    		this.name = name;
    	}

    	@Column(name = "[DESC]", nullable = false)
    	public String getDesc() {
    		return this.desc;
    	}

    	public void setDesc(String desc) {
    		this.desc = desc;
    	}

    	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    	public Set<Stock> getStocks() {
    		return this.stocks;
    	}

    	public void setStocks(Set<Stock> stocks) {
    		this.stocks = stocks;
    	}

    }

## 3\. Hibernate Configuration File

Puts annotated classes `Stock.java` and `Category.java` in `hibernate.cfg.xml` like this :

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
        <mapping class="com.mkyong.stock.Stock" />
        <mapping class="com.mkyong.stock.Category" />
    </session-factory>
    </hibernate-configuration>

## 4\. Run It

Run it, the result is self-explanatory.

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

            System.out.println("Hibernate many to many (Annotation)");
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

_Output_

    Hibernate many to many (Annotation)
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
            (`DESC`, NAME)
        values
            (?, ?)
    Hibernate:
        insert
        into
            mkyongdb.category
            (`DESC`, NAME)
        values
            (?, ?)
    Hibernate:
        insert
        into
            mkyongdb.stock_category
            (STOCK_ID, CATEGORY_ID)
        values
            (?, ?)
    Hibernate:
        insert
        into
            mkyongdb.stock_category
            (STOCK_ID, CATEGORY_ID)
        values
            (?, ?)
    Done

[http://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example-annotation/](http://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example-annotation/)

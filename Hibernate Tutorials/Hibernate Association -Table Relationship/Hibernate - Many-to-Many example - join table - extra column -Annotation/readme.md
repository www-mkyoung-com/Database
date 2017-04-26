In this tutorial, we show you how to use Hibernate to implements “many-to-many table relationship, with **extra column in the join table**“.

**Note**  
For many to many relationship with NO extra column in the join table, please refer to this [@many-to-many tutorial](http://www.mkyong.com/hibernate/hibernate-many-to-many-relationship-example-annotation/)

## 1\. Many-to-many table + extra columns in join table

The STOCK and CATEGORY many to many relationship is linked with a third / join table named STOCK_CATEGORY, with extra “`created_by`” and “`created_date`” columns.

![many to many diagram](http://www.mkyong.com/wp-content/uploads/2011/04/many-to-many-join-table-diagram.png)

_MySQL table script_

    CREATE TABLE `stock` (
      `STOCK_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
      `STOCK_CODE` VARCHAR(10) NOT NULL,
      `STOCK_NAME` VARCHAR(20) NOT NULL,
      PRIMARY KEY (`STOCK_ID`) USING BTREE,
      UNIQUE KEY `UNI_STOCK_NAME` (`STOCK_NAME`),
      UNIQUE KEY `UNI_STOCK_ID` (`STOCK_CODE`) USING BTREE
    )

    CREATE TABLE `category` (
      `CATEGORY_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
      `NAME` VARCHAR(10) NOT NULL,
      `DESC` VARCHAR(255) NOT NULL,
      PRIMARY KEY (`CATEGORY_ID`) USING BTREE
    )

    CREATE TABLE  `stock_category` (
      `STOCK_ID` INT(10) UNSIGNED NOT NULL,
      `CATEGORY_ID` INT(10) UNSIGNED NOT NULL,
      `CREATED_DATE` DATE NOT NULL,
      `CREATED_BY` VARCHAR(10) NOT NULL,
      PRIMARY KEY (`STOCK_ID`,`CATEGORY_ID`),
      CONSTRAINT `FK_CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`)
                 REFERENCES `category` (`CATEGORY_ID`),
      CONSTRAINT `FK_STOCK_ID` FOREIGN KEY (`STOCK_ID`)
                 REFERENCES `stock` (`STOCK_ID`)
    )

## 2\. Project Structure

Review the file project structure of this tutorial.

![many to many project folder](http://www.mkyong.com/wp-content/uploads/2011/04/many-to-many-join-table-folder.png)

## 3\. Hibernate / JPA Annotation

The Hibernate / JBoss tools generated annotation codes are not working in this third table extra column scenario. To make it works, you should customize the code to use “`@AssociationOverride`“, in `StockCategory.java` to represent the many to many relationship.

See following customized codes :

_File : Stock.java_

    package com.mkyong.stock;

    import java.util.HashSet;
    import java.util.Set;

    import javax.persistence.CascadeType;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.FetchType;
    import javax.persistence.GeneratedValue;
    import static javax.persistence.GenerationType.IDENTITY;
    import javax.persistence.Id;
    import javax.persistence.OneToMany;
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
    	private Set<StockCategory> stockCategories = new HashSet<StockCategory>(0);

    	public Stock() {
    	}

    	public Stock(String stockCode, String stockName) {
    		this.stockCode = stockCode;
    		this.stockName = stockName;
    	}

    	public Stock(String stockCode, String stockName,
    			Set<StockCategory> stockCategories) {
    		this.stockCode = stockCode;
    		this.stockName = stockName;
    		this.stockCategories = stockCategories;
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

    	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.stock", cascade=CascadeType.ALL)
    	public Set<StockCategory> getStockCategories() {
    		return this.stockCategories;
    	}

    	public void setStockCategories(Set<StockCategory> stockCategories) {
    		this.stockCategories = stockCategories;
    	}

    }

_File : StockCategory.java_

    package com.mkyong.stock;

    import java.util.Date;

    import javax.persistence.AssociationOverride;
    import javax.persistence.AssociationOverrides;
    import javax.persistence.Column;
    import javax.persistence.EmbeddedId;
    import javax.persistence.Entity;
    import javax.persistence.JoinColumn;
    import javax.persistence.Table;
    import javax.persistence.Temporal;
    import javax.persistence.TemporalType;
    import javax.persistence.Transient;

    @Entity
    @Table(name = "stock_category", catalog = "mkyongdb")
    @AssociationOverrides({
    		@AssociationOverride(name = "pk.stock",
    			joinColumns = @JoinColumn(name = "STOCK_ID")),
    		@AssociationOverride(name = "pk.category",
    			joinColumns = @JoinColumn(name = "CATEGORY_ID")) })
    public class StockCategory implements java.io.Serializable {

    	private StockCategoryId pk = new StockCategoryId();
    	private Date createdDate;
    	private String createdBy;

    	public StockCategory() {
    	}

    	@EmbeddedId
    	public StockCategoryId getPk() {
    		return pk;
    	}

    	public void setPk(StockCategoryId pk) {
    		this.pk = pk;
    	}

    	@Transient
    	public Stock getStock() {
    		return getPk().getStock();
    	}

    	public void setStock(Stock stock) {
    		getPk().setStock(stock);
    	}

    	@Transient
    	public Category getCategory() {
    		return getPk().getCategory();
    	}

    	public void setCategory(Category category) {
    		getPk().setCategory(category);
    	}

    	@Temporal(TemporalType.DATE)
    	@Column(name = "CREATED_DATE", nullable = false, length = 10)
    	public Date getCreatedDate() {
    		return this.createdDate;
    	}

    	public void setCreatedDate(Date createdDate) {
    		this.createdDate = createdDate;
    	}

    	@Column(name = "CREATED_BY", nullable = false, length = 10)
    	public String getCreatedBy() {
    		return this.createdBy;
    	}

    	public void setCreatedBy(String createdBy) {
    		this.createdBy = createdBy;
    	}

    	public boolean equals(Object o) {
    		if (this == o)
    			return true;
    		if (o == null || getClass() != o.getClass())
    			return false;

    		StockCategory that = (StockCategory) o;

    		if (getPk() != null ? !getPk().equals(that.getPk())
    				: that.getPk() != null)
    			return false;

    		return true;
    	}

    	public int hashCode() {
    		return (getPk() != null ? getPk().hashCode() : 0);
    	}
    }

_File : StockCategoryId.java_

    package com.mkyong.stock;

    import javax.persistence.Embeddable;
    import javax.persistence.ManyToOne;

    @Embeddable
    public class StockCategoryId implements java.io.Serializable {

    	private Stock stock;
        private Category category;

    	@ManyToOne
    	public Stock getStock() {
    		return stock;
    	}

    	public void setStock(Stock stock) {
    		this.stock = stock;
    	}

    	@ManyToOne
    	public Category getCategory() {
    		return category;
    	}

    	public void setCategory(Category category) {
    		this.category = category;
    	}

    	public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StockCategoryId that = (StockCategoryId) o;

            if (stock != null ? !stock.equals(that.stock) : that.stock != null) return false;
            if (category != null ? !category.equals(that.category) : that.category != null)
                return false;

            return true;
        }

        public int hashCode() {
            int result;
            result = (stock != null ? stock.hashCode() : 0);
            result = 31 * result + (category != null ? category.hashCode() : 0);
            return result;
        }

    }

_File : Category.java_

    package com.mkyong.stock;

    import java.util.HashSet;
    import java.util.Set;

    import javax.persistence.CascadeType;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.FetchType;
    import javax.persistence.GeneratedValue;
    import static javax.persistence.GenerationType.IDENTITY;
    import javax.persistence.Id;
    import javax.persistence.OneToMany;
    import javax.persistence.Table;

    @Entity
    @Table(name = "category", catalog = "mkyongdb")
    public class Category implements java.io.Serializable {

    	private Integer categoryId;
    	private String name;
    	private String desc;
    	private Set<StockCategory> stockCategories = new HashSet<StockCategory>(0);

    	public Category() {
    	}

    	public Category(String name, String desc) {
    		this.name = name;
    		this.desc = desc;
    	}

    	public Category(String name, String desc, Set<StockCategory> stockCategories) {
    		this.name = name;
    		this.desc = desc;
    		this.stockCategories = stockCategories;
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

    	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.category")
    	public Set<StockCategory> getStockCategories() {
    		return this.stockCategories;
    	}

    	public void setStockCategories(Set<StockCategory> stockCategories) {
    		this.stockCategories = stockCategories;
    	}

    }

Done, the many to many relationship should be working now.

## 4\. Run it – Case 1

For a new category and a new stock.

    session.beginTransaction();

    Stock stock = new Stock();
    stock.setStockCode("7052");
    stock.setStockName("PADINI");

    Category category1 = new Category("CONSUMER", "CONSUMER COMPANY");
    //new category, need save to get the id first
    session.save(category1);

    StockCategory stockCategory = new StockCategory();
    stockCategory.setStock(stock);
    stockCategory.setCategory(category1);
    stockCategory.setCreatedDate(new Date()); //extra column
    stockCategory.setCreatedBy("system"); //extra column

    stock.getStockCategories().add(stockCategory);

    session.save(stock);

    session.getTransaction().commit();

_Output…_

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
            mkyongdb.stock
            (STOCK_CODE, STOCK_NAME)
        values
            (?, ?)
    Hibernate:
        select
            stockcateg_.CATEGORY_ID,
            stockcateg_.STOCK_ID,
            stockcateg_.CREATED_BY as CREATED1_2_,
            stockcateg_.CREATED_DATE as CREATED2_2_
        from
            mkyongdb.stock_category stockcateg_
        where
            stockcateg_.CATEGORY_ID=?
            and stockcateg_.STOCK_ID=?
    Hibernate:
        insert
        into
            mkyongdb.stock_category
            (CREATED_BY, CREATED_DATE, CATEGORY_ID, STOCK_ID)
        values
            (?, ?, ?, ?)

## 5\. Run it – Case 2

Get an existing category and a new stock.

    session.beginTransaction();

     Stock stock = new Stock();
     stock.setStockCode("7052");
     stock.setStockName("PADINI");

     //assume category id is 7
     Category category1 = (Category)session.get(Category.class, 7);

     StockCategory stockCategory = new StockCategory();
     stockCategory.setStock(stock);
     stockCategory.setCategory(category1);
     stockCategory.setCreatedDate(new Date()); //extra column
     stockCategory.setCreatedBy("system"); //extra column

     stock.getStockCategories().add(stockCategory);

     session.save(stock);

     session.getTransaction().commit();

_Output…_

    Hibernate:
        select
            category0_.CATEGORY_ID as CATEGORY1_1_0_,
            category0_.`DESC` as DESC2_1_0_,
            category0_.NAME as NAME1_0_
        from
            mkyongdb.category category0_
        where
            category0_.CATEGORY_ID=?
    Hibernate:
        insert
        into
            mkyongdb.stock
            (STOCK_CODE, STOCK_NAME)
        values
            (?, ?)
    Hibernate:
        select
            stockcateg_.CATEGORY_ID,
            stockcateg_.STOCK_ID,
            stockcateg_.CREATED_BY as CREATED1_2_,
            stockcateg_.CREATED_DATE as CREATED2_2_
        from
            mkyongdb.stock_category stockcateg_
        where
            stockcateg_.CATEGORY_ID=?
            and stockcateg_.STOCK_ID=?
    Hibernate:
        insert
        into
            mkyongdb.stock_category
            (CREATED_BY, CREATED_DATE, CATEGORY_ID, STOCK_ID)
        values
            (?, ?, ?, ?)

Done.

[http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/](http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/)

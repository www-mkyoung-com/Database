## What is dynamic-insert

The dynamic-insert attribute tells Hibernate whether to include null properties in the SQL INSERT statement. Let explore some examples to understand more clear about it.

## Dynamic-insert example

## 1\. dynamic-insert=false

The default value of dynamic-insert is false, which means **include null properties **in the Hibernate’s SQL INSERT statement.

For example, try set some null values to an object properties and save it.

    StockTransaction stockTran = new StockTransaction();
    //stockTran.setPriceOpen(new Float("1.2"));
    //stockTran.setPriceClose(new Float("1.1"));
    //stockTran.setPriceChange(new Float("10.0"));
    stockTran.setVolume(2000000L);
    stockTran.setDate(new Date());
    stockTran.setStock(stock);

    session.save(stockTran);

Turn on the Hibernate “show_sql” to true, you will see the following insert SQL statement.

    Hibernate:
        insert
        into
            mkyong.stock_transaction
            (DATE, PRICE_CHANGE, PRICE_CLOSE, PRICE_OPEN, STOCK_ID, VOLUME)
        values
            (?, ?, ?, ?, ?, ?)

**Hibernate will generate the unnecessary columns** (PRICE_CHANGE, PRICE_CLOSE, PRICE_OPEN) for the insertion.

## 2\. dynamic-insert=true

If set the dynamic-insert to true, which means **exclude null property values** in the Hibernate’s SQL INSERT statement.

For example, try set some null values to an object properties and save it again.

    StockTransaction stockTran = new StockTransaction();
    //stockTran.setPriceOpen(new Float("1.2"));
    //stockTran.setPriceClose(new Float("1.1"));
    //stockTran.setPriceChange(new Float("10.0"));
    stockTran.setVolume(2000000L);
    stockTran.setDate(new Date());
    stockTran.setStock(stock);

    session.save(stockTran);

Turn on the Hibernate “show_sql” to true. You will see the different insert SQL statement.

    Hibernate:
        insert
        into
            mkyong.stock_transaction
            (DATE, STOCK_ID, VOLUME)
        values
            (?, ?, ?)

**Hibernate will generate only the necessary columns** (DATE, STOCK_ID, VOLUME) for the insertion.

## Performance issue

In certain situations, such as a very large table with hundreds of columns (legacy design), or a table contains extremely large data volume, insert something not necessary definitely will drop down your system performance.

## How to configure it

You can configure the dynamic-insert properties value through annotation or XML mapping file.

## 1\. Annotation

    @Entity
    @Table(name = "stock_transaction", catalog = "mkyong")
    @org.hibernate.annotations.Entity(
    		dynamicInsert = true
    )
    public class StockTransaction implements java.io.Serializable {

## 2\. XML mapping

    <class ... table="stock_transaction" catalog="mkyong" dynamic-insert="true">
            <id name="tranId" type="java.lang.Integer">
                <column name="TRAN_ID" />
                <generator class="identity" />
            </id>

## Conclusion

This little “**dynamic-insert**” tweak may increase your system performance, and highly recommends to do it. However, one question in my mind is why Hibernate set it to false by default?

## Follow up

1\. Hibernate – [dynamic-update attribute](http://www.mkyong.com/hibernate/hibernate-dynamic-update-attribute-example/) example

[http://www.mkyong.com/hibernate/hibernate-dynamic-insert-attribute-example/](http://www.mkyong.com/hibernate/hibernate-dynamic-insert-attribute-example/)

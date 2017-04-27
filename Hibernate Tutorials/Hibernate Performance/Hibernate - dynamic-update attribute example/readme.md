## What is dynamic-update

The dynamic-update attribute tells Hibernate whether to include unmodified properties in the SQL UPDATE statement.

## Dynamic-update example

## 1\. dynamic-update=false

The default value of dynamic-update is false, which means **include unmodified properties** in the Hibernate’s SQL update statement.

For example, get an object and try modify its value and update it.

    Query q = session.createQuery("from StockTransaction where tranId = :tranId ");
    q.setParameter("tranId", 11);
    StockTransaction stockTran = (StockTransaction)q.list().get(0);

    stockTran.setVolume(4000000L);
    session.update(stockTran);

Hibernate will generate the following update SQL statement.

    Hibernate:
        update
            mkyong.stock_transaction
        set
            DATE=?,
            PRICE_CHANGE=?,
            PRICE_CLOSE=?,
            PRICE_OPEN=?,
            STOCK_ID=?,
            VOLUME=?
        where
            TRAN_ID=?

Hibernate will update all the unmodified columns.

## 2\. dynamic-update=true

If set the dynamic-insert to true, which means **exclude unmodified properties** in the Hibernate’s SQL update statement.

For example, get an object and try modify its value and update it again.

    Query q = session.createQuery("from StockTransaction where tranId = :tranId ");
    q.setParameter("tranId", 11);
    StockTransaction stockTran = (StockTransaction)q.list().get(0);

    stockTran.setVolume(4000000L);
    session.update(stockTran);

Hibernate will generate different update SQL statement.

    Hibernate:
        update
            mkyong.stock_transaction
        set
            VOLUME=?
        where
            TRAN_ID=?

Hibernate will update the modified columns only.

**Performance issue**  
In a large table with many columns (legacy design) or contains large data volumes, update some unmodified columns are absolutely unnecessary and great impact on the system performance.

## How to configure it

You can configure “`dynamic-update`” properties via annotation or XML mapping file.

## 1\. Annotation

    @Entity
    @Table(name = "stock_transaction", catalog = "mkyong")
    @org.hibernate.annotations.Entity(
    		dynamicUpdate = true
    )
    public class StockTransaction implements java.io.Serializable {

## 2\. XML mapping

    <class ... table="stock_transaction" catalog="mkyong" dynamic-update="true">
            <id name="tranId" type="java.lang.Integer">
                <column name="TRAN_ID" />
                <generator class="identity" />
            </id>

## Conclusion

This little “**dynamic-update**” tweak will definitely increase your system performance, and highly recommended to do it.

## Follow up

1\. Hibernate – [dynamic-insert attribute](http://www.mkyong.com/hibernate/hibernate-dynamic-insert-attribute-example/) example

[http://www.mkyong.com/hibernate/hibernate-dynamic-update-attribute-example/](http://www.mkyong.com/hibernate/hibernate-dynamic-update-attribute-example/)

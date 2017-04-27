Hibernate created a new language named Hibernate Query Language (HQL), the syntax is quite similar to database SQL language. The main difference between is **HQL uses class name instead of table name, and property names instead of column name**.

HQL is extremely simple to learn and use, and the code is always self-explanatory.

## 1\. HQL Select Query Example

Retrieve a stock data where stock code is “7277”.

    Query query = session.createQuery("from Stock where stockCode = :code ");
    query.setParameter("code", "7277");
    List list = query.list();

    Query query = session.createQuery("from Stock where stockCode = '7277' ");
    List list = query.list();

## 2\. HQL Update Query Example

Update a stock name to “DIALOG1” where stock code is “7277”.

    Query query = session.createQuery("update Stock set stockName = :stockName" +
        				" where stockCode = :stockCode");
    query.setParameter("stockName", "DIALOG1");
    query.setParameter("stockCode", "7277");
    int result = query.executeUpdate();

    Query query = session.createQuery("update Stock set stockName = 'DIALOG2'" +
        				" where stockCode = '7277'");
    int result = query.executeUpdate();

## 3\. HQL Delete Query Example

Delete a stock where stock code is “7277”.

    Query query = session.createQuery("delete Stock where stockCode = :stockCode");
    query.setParameter("stockCode", "7277");
    int result = query.executeUpdate();

    Query query = session.createQuery("delete Stock where stockCode = '7277'");
    int result = query.executeUpdate();

## 4\. HQL Insert Query Example

In HQL, only the INSERT INTO … SELECT … is supported; there is no INSERT INTO … VALUES. HQL only support insert from another table. For example

    "insert into Object (id, name) select oo.id, oo.name from OtherObject oo";

Insert a stock record from another backup_stock table. This can also called bulk-insert statement.

    Query query = session.createQuery("insert into Stock(stock_code, stock_name)" +
        			"select stock_code, stock_name from backup_stock");
    int result = query.executeUpdate();

The **query.executeUpdate()** will return how many number of record has been inserted, updated or deleted.

## Reference

1.  [Hibernate 3.3.2 query documentation](http://docs.jboss.org/hibernate/core/3.3/reference/en/html/objectstate.html#objectstate-querying)

[http://www.mkyong.com/hibernate/hibernate-query-examples-hql/](http://www.mkyong.com/hibernate/hibernate-query-examples-hql/)

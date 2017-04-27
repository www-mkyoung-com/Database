In Hibernate, HQL or criteria queries should be able to let you to execute almost any SQL query you want. However, many developers are complaint about the Hibernate’s generated SQL statement is slow and more prefer to generated their own SQL (native SQL) statement.

## Native SQL queries example

Hibernate provide a **createSQLQuery** method to let you call your native SQL statement directly.

1\. In this example, you tell Hibernate to return you a Stock.class, all the select data (*) will match to your Stock.class properties automatically.

    Query query = session.createSQLQuery(
    "select * from stock s where s.stock_code = :stockCode")
    .addEntity(Stock.class)
    .setParameter("stockCode", "7277");
    List result = query.list();

2\. In this example, Hibernate will return you an Object array.

    Query query = session.createSQLQuery(
    "select s.stock_code from stock s where s.stock_code = :stockCode")
    .setParameter("stockCode", "7277");
    List result = query.list();

Alternative, you also can use the **named query** to call your native SQL statement. See[ Hibernate named query examples here](http://www.mkyong.com/hibernate/hibernate-named-query-examples/).

## Hibernate’s generated SQL statement is slow !?

I do not agreed on the statement “Hibernate’s generated SQL statement is slow”. Often times, i found out this is because of the developers do not understand the table relationship well, and did some wrong table mappings or misuse the fetch strategies. It will cause Hibernate generated some unnecessary SQL statements or join some unnecessary tables… And developers like to take this excuse and create their own SQL statement to quick fix the bug, and didn’t aware of the core problem will causing more bugs awaiting.

## Conclusion

I admit sometime the native SQL statement is really more convenient and easy than HQL, but, do think carefully why you need a native SQL statement? Is this really Hibernate cant do it? If yes, then go ahead ~

_P.S In Oracle database, i more prefer to use native SQL statement in many critical performance queries, because i need to put the “hint” to improve the Oracle query performance._

[http://www.mkyong.com/hibernate/hibernate-native-sql-queries-examples/](http://www.mkyong.com/hibernate/hibernate-native-sql-queries-examples/)

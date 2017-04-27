Without parameter binding, you have to concatenate the parameter String like this (bad code) :

    String hql = "from Stock s where s.stockCode = '" + stockCode + "'";
    List result = session.createQuery(hql).list();

Pass an unchecked value from user input to the database will raise security concern, because it can easy get hack by SQL injection. You have to avoid the above bad code and using parameter binding instead.

## Hibernate parameter binding

There are two ways to parameter binding : named parameters or positional.

## 1\. Named parameters

This is the most common and user friendly way. It use colon followed by a parameter name (:example) to define a named parameter. See examples…

Example 1 – setParameter

The **setParameter** is smart enough to discover the parameter data type for you.

    String hql = "from Stock s where s.stockCode = :stockCode";
    List result = session.createQuery(hql)
    .setParameter("stockCode", "7277")
    .list();

Example 2 – setString

You can use **setString** to tell Hibernate this parameter date type is String.

    String hql = "from Stock s where s.stockCode = :stockCode";
    List result = session.createQuery(hql)
    .setString("stockCode", "7277")
    .list();

Example 3 – setProperties

This feature is great ! You can pass an object into the parameter binding. Hibernate will automatic check the object’s properties and match with the colon parameter.

    Stock stock = new Stock();
    stock.setStockCode("7277");
    String hql = "from Stock s where s.stockCode = :stockCode";
    List result = session.createQuery(hql)
    .setProperties(stock)
    .list();

## 2\. Positional parameters

It’s use question mark (?) to define a named parameter, and you have to set your parameter according to the position sequence. See example…

    String hql = "from Stock s where s.stockCode = ? and s.stockName = ?";
    List result = session.createQuery(hql)
    .setString(0, "7277")
    .setParameter(1, "DIALOG")
    .list();

This approach is not support the **setProperties** function. In addition, it’s vulnerable to easy breakage because every change of the position of the bind parameters requires a change to the parameter binding code.

    String hql = "from Stock s where s.stockName = ? and s.stockCode = ?";
    List result = session.createQuery(hql)
    .setParameter(0, "DIALOG")
    .setString(1, "7277")
    .list();

## Conclusion

In Hibernate parameter binding, i would recommend always go for “**Named parameters**“, as it’s more easy to maintain, and the compiled SQL statement can be reuse (if only bind parameters change) to increase the performance.

[http://www.mkyong.com/hibernate/hibernate-parameter-binding-examples/](http://www.mkyong.com/hibernate/hibernate-parameter-binding-examples/)

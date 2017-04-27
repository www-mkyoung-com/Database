In Hibernate, when you try to save an object into a table with any database reserved keyword as column name, you may hit the following error …

    ERROR JDBCExceptionReporter:78 - You have an error in your SQL syntax;
    check the manual that corresponds to your MySQL server version for the
    right syntax to use near 'Datadabase reserved keyword....

## Reserved keyword “DESC”

In MySQL, “DESC” is the reserved keyword. Let see some examples to demonstrate how to use this reserved keyword in Hibernate.

## Hibernate XML Mapping file

This is the default XML mapping file implementation for a table column, it will cause JDBCException…

    <property name="desc" type="string" >
        <column name="DESC" length="255" not-null="true" />
    </property>

## Solution

1\. Enclose the keyword with square brackets [].

    <property name="desc" type="string" >
        <column name="[DESC]" length="255" not-null="true" />
    </property>

2\. Use single quote(‘) to enclose the double quotes (“)

    <property name="desc" type="string" >
        <column name='"DESC"' length="255" not-null="true" />
    </property>

## Hibernate Annotation

This is the default annotation implementation for a table column, it will cause JDBCException…

    @Column(name = "DESC", nullable = false)
    public String getDesc() {
    	return this.desc;
    }

## Solution

1\. Enclose the keyword with square brackets [].

    @Column(name = "[DESC]", nullable = false)
    public String getDesc() {
    	return this.desc;
    }

2\. Use double quotes (“) to enclose it.

    @Column(name = "\"DESC\"", nullable = false)
    public String getDesc() {
    	return this.desc;
    }

## Conclusion

This same solution can also applied to the reserved keyword as table name.

[http://www.mkyong.com/hibernate/how-to-use-database-reserved-keyword-in-hibernate/](http://www.mkyong.com/hibernate/how-to-use-database-reserved-keyword-in-hibernate/)

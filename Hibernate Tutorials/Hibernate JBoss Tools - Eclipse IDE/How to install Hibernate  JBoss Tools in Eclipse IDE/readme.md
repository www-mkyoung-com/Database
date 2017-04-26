[Hibernate Tools](http://www.hibernate.org/subprojects/tools.html) is a handy tool for Java’s developers to generate tedious hibernate related stuffs like mapping files and annotation code. The common use case is the “reverse engineering” feature to generate Hibernate model class, hbm mapping file or annotation code from database tables.

**Note**  
Hibernate Tools is bundled as the core component of JBoss Tools. So, after installed JBoss tools, you installed the Hibernate tools as well.

Here’s a guide to show you how to install Hibernate / JBoss Tools in Eclipse IDE.

## 1\. Know your Eclipse & JBoss Tools version to download

First, you have to find out the correct version of Hibernate/JBoss tool for your Eclipse IDE. Go here – [http://www.jboss.org/tools/download](http://www.jboss.org/tools/download) for the available combination version to download.

For example,

1.  If you are using Eclipse 3.6 / Helios , download JBoss Tools 3.2
2.  If you are using Eclipse 3.5 / Galileo, download JBoss Tools 3.1

## 2\. Eclipse update site for JBoss Tools

Point to your correct version, right click to copy the Eclipse update site for JBoss tools. For Eclipse 3.6, the URL is ” _http://download.jboss.org/jbosstools/updates/stable/helios/_ ”

## 3\. Install It

In Eclipse IDE, menu bar, select “**Help**” >> “**Install New Software …**” , put the Eclipse update site URL.

![JBoss tools in Eclipse](http://www.mkyong.com/wp-content/uploads/2009/12/jboss-tool-eclipse.png)

Type “**hibernate**” in the filter box, to list down the necessary components for Hibernate tools. Select all the “**Hibernate Tools**” components and click next to download.

![JBoss tools in Eclipse 2](http://www.mkyong.com/wp-content/uploads/2009/12/jboss-tool-eclipse-2.png)

**Warning**  
Do not select all components, it will take much longer time download many unnecessary components. You want Hibernate tools only, not others.

## 4\. Restart Eclipse

After the download progress is completed, restart Eclipse to take effect.

## 5\. Verification

If Hibernate tools is installed properly, you are able to see the “**Hibernate Perspective**” in “**Windows**” >> “**Open Perspective**” >> “**Others**“.

![Hibernate Perspective in Eclipse IDE](http://www.mkyong.com/wp-content/uploads/2009/12/jboss-tool-eclipse-hibernate-view.png)

Done.

## Reference

1.  [http://www.hibernate.org/subprojects/tools.html](http://www.hibernate.org/subprojects/tools.html)
2.  [http://www.jboss.org/tools/download](http://www.jboss.org/tools/download)

[http://www.mkyong.com/hibernate/how-to-install-hibernate-tools-in-eclipse-ide/](http://www.mkyong.com/hibernate/how-to-install-hibernate-tools-in-eclipse-ide/)

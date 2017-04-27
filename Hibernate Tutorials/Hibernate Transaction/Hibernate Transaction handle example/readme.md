In Hibernate, the transaction management is quite standard, just remember any exceptions thrown by Hibernate are **FATAL**, you have to roll back the transaction and close the current session immediately.

Here’s a Hibernate transaction template :

    Session session = null;
    Transaction tx = null;

    try{
    	session = HibernateUtil.getSessionFactory().openSession();
    	tx = session.beginTransaction();
    	tx.setTimeout(5);

    	//doSomething(session);

    	tx.commit();

    }catch(RuntimeException e){
    	try{
    		tx.rollback();
    	}catch(RuntimeException rbe){
    		log.error("Couldn’t roll back transaction", rbe);
    	}
    	throw e;
    }finally{
    	if(session!=null){
    		session.close();
    	}
    }

[http://www.mkyong.com/hibernate/hibernate-transaction-handle-example/](http://www.mkyong.com/hibernate/hibernate-transaction-handle-example/)

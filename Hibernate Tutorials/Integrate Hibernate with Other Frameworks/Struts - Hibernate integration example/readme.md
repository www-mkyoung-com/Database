**Steps of the integration :**

1.  Create a new **Hibernate Struts plug-in** file to set the Hibernate session factory in servlet context, and include this file in **struts-config.xml** file.
2.  In Struts, get the **Hibernate session factory from servlet context**, and do whatever Hibernate task you want.

## 1\. Hibernate Struts Plug-in

Create a Hibernate Struts Plug-in, get the Hibernate session factory, store it into the servlet context for later user – **servlet.getServletContext().setAttribute(KEY_NAME, factory);**.

    package com.mkyong.common.plugin;

    import java.net.URL;
    import javax.servlet.ServletException;

    import org.apache.struts.action.ActionServlet;
    import org.apache.struts.action.PlugIn;
    import org.apache.struts.config.ModuleConfig;
    import org.hibernate.HibernateException;
    import org.hibernate.MappingException;
    import org.hibernate.SessionFactory;
    import org.hibernate.cfg.Configuration;

    public class HibernatePlugin implements PlugIn {
       private Configuration config;
       private SessionFactory factory;
       private String path = "/hibernate.cfg.xml";
       private static Class clazz = HibernatePlugin.class;

       public static final String KEY_NAME = clazz.getName();

       public void setPath(String path) {
          this.path = path;
       }

       public void init(ActionServlet servlet, ModuleConfig modConfig)
          throws ServletException {

          try {

        	 //save the Hibernate session factory into serlvet context
             URL url = HibernatePlugin.class.getResource(path);
             config = new Configuration().configure(url);
             factory = config.buildSessionFactory();
             servlet.getServletContext().setAttribute(KEY_NAME, factory);

          } catch (MappingException e) {
             throw new ServletException();
          } catch (HibernateException e) {
             throw new ServletException();
          }

       }

       public void destroy() {
          try {
             factory.close();
          } catch (HibernateException e) {
             e.printStackTrace();
          }
       }
    }

## 2\. struts-config.xml

Include the **Hibernate Struts plug-in** into the Struts configuration file (**struts-config.xml**).

    <struts-config>
        ...
        <plug-in className="com.mkyong.common.plugin.HibernatePlugin">
          	<set-property property="path" value="/hibernate.cfg.xml"/>
        </plug-in>
    	...
    <struts-config>

## 3\. Get the Hibernate session factory

In Struts action class, you can get the **Hibernate session factory from servlet context**.

    servlet.getServletContext().getAttribute(HibernatePlugin.KEY_NAME);

and do whatever Hibernate task as normal.

    package com.mkyong.customer.action;

    import java.util.Date;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    import org.apache.commons.beanutils.BeanUtils;
    import org.apache.struts.action.Action;
    import org.apache.struts.action.ActionForm;
    import org.apache.struts.action.ActionForward;
    import org.apache.struts.action.ActionMapping;
    import org.hibernate.Session;
    import org.hibernate.SessionFactory;

    import com.mkyong.common.plugin.HibernatePlugin;
    import com.mkyong.customer.form.CustomerForm;
    import com.mkyong.customer.model.Customer;

    public class AddCustomerAction extends Action{

      public ActionForward execute(ActionMapping mapping,ActionForm form,
    	HttpServletRequest request,HttpServletResponse response)
      throws Exception {

            SessionFactory sessionFactory =
    	         (SessionFactory) servlet.getServletContext()
                                .getAttribute(HibernatePlugin.KEY_NAME);

    	Session session = sessionFactory.openSession();

    	CustomerForm customerForm = (CustomerForm)form;
    	Customer customer = new Customer();

    	//copy customerform to model
    	BeanUtils.copyProperties(customer, customerForm);

    	//save it
    	customer.setCreatedDate(new Date());

    	session.beginTransaction();
    	session.save(customer);
    	session.getTransaction().commit();

    	return mapping.findForward("success");

      }
    }

Done.

[http://www.mkyong.com/struts/struts-hibernate-integration-example/](http://www.mkyong.com/struts/struts-hibernate-integration-example/)

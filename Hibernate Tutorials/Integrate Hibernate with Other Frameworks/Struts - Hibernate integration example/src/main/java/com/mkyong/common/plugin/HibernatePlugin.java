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

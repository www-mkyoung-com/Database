package com.mkyong.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.hibernate.Session;
import com.mkyong.persistence.HibernateUtil;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Hibernate save image into database");
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        
        //save image into database
    	File file = new File("C:\\mavan-spring-hibernate-annotation-mysql.gif");
        byte[] bFile = new byte[(int) file.length()];
        
        try {
	        FileInputStream fileInputStream = new FileInputStream(file);
	        //convert file into array of bytes
	        fileInputStream.read(bFile);
	        fileInputStream.close();
        } catch (Exception e) {
	        e.printStackTrace();
        }
        
        Avatar avatar = new Avatar();
        avatar.setImage(bFile);
        
        session.save(avatar);
        
        //Get image from database
        Avatar avatar2 = (Avatar)session.get(Avatar.class, avatar.getAvatarId());
        byte[] bAvatar = avatar2.getImage();
        
        try{
        	FileOutputStream fos = new FileOutputStream("C:\\test.gif"); 
            fos.write(bAvatar);
            fos.close();
        }catch(Exception e){
        	e.printStackTrace();
        }

        session.getTransaction().commit();
    }
}

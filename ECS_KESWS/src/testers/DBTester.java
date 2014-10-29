/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testers;
 
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.controllers.RecCdFileMsgJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author kim
 */
public class DBTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RecCdFileMsg recCDFileMsg=new RecCdFileMsg();
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("ECS-KESWSPU");
        RecCdFileMsgJpaController recCdFileMsgController= new RecCdFileMsgJpaController(emf);
      recCdFileMsgController.create(recCDFileMsg);
    }
    
}

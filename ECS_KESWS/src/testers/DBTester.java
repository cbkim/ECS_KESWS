/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testers;
 
import databaselayer.ecs_kesws.entities.CdFileDetails;
import databaselayer.ecs_kesws.entities.EcsDocumentTypes;
import databaselayer.ecs_kesws.entities.EcsResCdFileMsg;
import databaselayer.ecs_kesws.entities.InternalProductcodes;
import databaselayer.ecs_kesws.entities.KeswsInspectors;
import databaselayer.ecs_kesws.entities.LogTypes;
import databaselayer.ecs_kesws.entities.MessageTypes;
import databaselayer.ecs_kesws.entities.Pricelist;
import databaselayer.ecs_kesws.entities.PricelistInternalProductcodeDocumentMap;
import databaselayer.ecs_kesws.entities.RecCdFileMsg;
import databaselayer.ecs_kesws.entities.RecErrorMsg;
import databaselayer.ecs_kesws.entities.RecPaymentMsg;
import databaselayer.ecs_kesws.entities.ResCdFileMsg;
import databaselayer.ecs_kesws.entities.ResPaymentMsg;
import databaselayer.ecs_kesws.entities.TransactionLogs;
import databaselayer.ecs_kesws.entities.Users;
import databaselayer.ecs_kesws.entities.controllers.CdFileDetailsJpaController;
import databaselayer.ecs_kesws.entities.controllers.EcsDocumentTypesJpaController;
import databaselayer.ecs_kesws.entities.controllers.EcsResCdFileMsgJpaController;
import databaselayer.ecs_kesws.entities.controllers.InternalProductcodesJpaController;
import databaselayer.ecs_kesws.entities.controllers.KeswsInspectorsJpaController;
import databaselayer.ecs_kesws.entities.controllers.LogTypesJpaController;
import databaselayer.ecs_kesws.entities.controllers.MessageTypesJpaController;
import databaselayer.ecs_kesws.entities.controllers.PricelistInternalProductcodeDocumentMapJpaController;
import databaselayer.ecs_kesws.entities.controllers.PricelistJpaController;
import databaselayer.ecs_kesws.entities.controllers.RecCdFileMsgJpaController;
import databaselayer.ecs_kesws.entities.controllers.RecErrorMsgJpaController;
import databaselayer.ecs_kesws.entities.controllers.RecPaymentMsgJpaController;
import databaselayer.ecs_kesws.entities.controllers.ResCdFileMsgJpaController;
import databaselayer.ecs_kesws.entities.controllers.ResPaymentMsgJpaController;
import databaselayer.ecs_kesws.entities.controllers.TransactionLogsJpaController;
import databaselayer.ecs_kesws.entities.controllers.UsersJpaController;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author NYOTA
 */
public class DBTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ECS-KESWSPU");
        //messagetype primary key: change key when testing
        int messageTypeKey = 26; 
        //change consignement ID when testing13
        int ecsConsignementIdRef = 28; 
       
        //INSERT A RECORD INTO THE TABLE Message_Type
        //The record has only the primary key
        MessageTypes messageTypes = new MessageTypes(messageTypeKey);
        MessageTypesJpaController control = new MessageTypesJpaController(emf);
        try {
            control.create(messageTypes);
        } catch (Exception ex) {
           Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //INSERT INTO rec_cd_file_msg    Table
        RecCdFileMsg recCdFileMsg = new RecCdFileMsg(new MessageTypes(messageTypeKey), "Test By Nyota 1", new Date(), 1, ecsConsignementIdRef);
        RecCdFileMsgJpaController controller= new RecCdFileMsgJpaController(emf);
        controller.create(recCdFileMsg);

        //INSERT INTO RES_PAYMENT_MSG
        ResPaymentMsg resPaymentMsg = new ResPaymentMsg(recCdFileMsg);
        ResPaymentMsgJpaController resPaymentMsgJpaController = new ResPaymentMsgJpaController(emf);
        resPaymentMsgJpaController.create(resPaymentMsg);
        
        //INSERT INTO REC_PAYMENT_MSG
        RecPaymentMsg recPaymentMsg = new RecPaymentMsg(resPaymentMsg, new Date());
        RecPaymentMsgJpaController recPaymentMsgJpaController = new RecPaymentMsgJpaController(emf);
        recPaymentMsgJpaController.create(recPaymentMsg);
        recPaymentMsgJpaController.create(recPaymentMsg);
        
        //INSERT INTO LOG_TYPES
        LogTypes logTypes = new LogTypes(1, "Servere", null);
        LogTypesJpaController logTypesJpaController = new LogTypesJpaController(emf);
        try {
            logTypesJpaController.create(logTypes);
        } catch (Exception ex) {
            Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         //ECS_DOCUMENT_TYPES
        EcsDocumentTypes ecsDocumentTypes = new EcsDocumentTypes(10);
        EcsDocumentTypesJpaController ecsDocumentTypesJpaController = new EcsDocumentTypesJpaController(emf);
        try {
            ecsDocumentTypesJpaController.create(ecsDocumentTypes);
        } catch (Exception ex) {
            Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         //INTERNAL_PRODUCTCODES
        InternalProductcodes internalProductcodes = new InternalProductcodes("HSCODE","INTCODE","hscodeDesc", "commodityCategory", "commodityType", "commodityTechnicalName","commodityVariety", "commodityForm", 122, 123, new Date(), new Date(), null);
        InternalProductcodesJpaController InternalProductcodesJpaController = new InternalProductcodesJpaController(emf);
        InternalProductcodesJpaController.create(internalProductcodes);
        
        //PRICELIST
        Pricelist  pricelist = new Pricelist("PCODE", 1000.0, "chargeDescription", new Date());
        PricelistJpaController PricelistJpaController = new PricelistJpaController(emf);
        PricelistJpaController.create(pricelist);
        
        //PRICELIST_INTERNAL_PRODUCTCODE_DOCUMENT_MAP
         PricelistInternalProductcodeDocumentMap pipd = new PricelistInternalProductcodeDocumentMap(ecsDocumentTypes,internalProductcodes,pricelist);
         PricelistInternalProductcodeDocumentMapJpaController pipdcontroller = new PricelistInternalProductcodeDocumentMapJpaController(emf);
         pipdcontroller.create(pipd);
                
         //CD_FILE_DETAILS
        CdFileDetails cdFileDetails = new CdFileDetails(12, ecsDocumentTypes, pipd, recCdFileMsg);
        CdFileDetailsJpaController cdFileDetailsJpaController = new CdFileDetailsJpaController(emf);
        try {
            cdFileDetailsJpaController.create(cdFileDetails);
        } catch (Exception ex) {
            Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        //ECS_RES_CD_FILE_MSG
        EcsResCdFileMsg ecsResCdFileMsg = new EcsResCdFileMsg("ecsResCdFileMsgUcrNo", null);
        EcsResCdFileMsgJpaController ecsResCdFileMsgJpaController = new EcsResCdFileMsgJpaController(emf);
        ecsResCdFileMsgJpaController.create(ecsResCdFileMsg);
            
        //KESWS_INSPECTORS
        KeswsInspectors keswsInspectors = new KeswsInspectors (452, "ecsId", "ecsName", "keswsName");
        KeswsInspectorsJpaController keswsInspectorsJpaController = new KeswsInspectorsJpaController(emf);
        try {
            keswsInspectorsJpaController.create(keswsInspectors);
        } catch (Exception ex) {
            Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       //RES_CD_FILE_MSG
       ResCdFileMsg resCdFileMsg = new ResCdFileMsg(messageTypes, recCdFileMsg);
       ResCdFileMsgJpaController resCdFileMsgJpaController = new ResCdFileMsgJpaController(emf);
       resCdFileMsgJpaController.create(resCdFileMsg);
        
        //REC_ERROR_MSG
        RecErrorMsg recErrorMsg = new RecErrorMsg(404, ecsResCdFileMsg, resCdFileMsg, resPaymentMsg);
        RecErrorMsgJpaController RecErrorMsgJpaController = new RecErrorMsgJpaController(emf);
        try {
            RecErrorMsgJpaController.create(recErrorMsg);
        } catch (Exception ex) {
            Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
       //TRANSACTION_LOGS
        TransactionLogs transactionLogs = new TransactionLogs(logTypes, resCdFileMsg, "logDetails", new Date());
        TransactionLogsJpaController transactionLogsJpaController = new TransactionLogsJpaController(emf);
        transactionLogsJpaController.create(transactionLogs);
        
                
                
        //USERS
       Users users = new Users("admin", "admin", "mosesnyota@gmail.com");
       UsersJpaController usersJpaController = new UsersJpaController(emf);
        try {
            usersJpaController.create(users);
        } catch (Exception ex) {
            Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
}
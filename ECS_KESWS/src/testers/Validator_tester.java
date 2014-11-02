/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testers;

import ECSKESWSService.ConfigMapper;
import xmlmapper.XMLFileValidator;

/**
 *
 * @author kim
 */
public class Validator_tester {

    public static void main(String[] args) {
        new ConfigMapper();
        XMLFileValidator xmlvalidator = new XMLFileValidator();
        xmlvalidator.validateAgainstXSD(ConfigMapper.getInboxFolder() + "CD2014KEPHISKEPHIS010000017314_1.xml", "/ecs_kesws/service/MDA_CommonTypes1.xsd", "/ecs_kesws/service/CONDOC1.xsd");
        System.err.println(xmlvalidator.getErrorDetails());

    }
}

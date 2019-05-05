/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package test;

import lightsearch.server.cmd.client.processor.debug.SoftCheckDebug;
import lightsearch.server.cmd.client.processor.debug.SoftCheckDebugInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SoftCheckDebugTestNG {
    
    @Test
    public void openSoftCheck() {
        testBegin("SoftCheckDebug", "openSoftCheck()");
        
        SoftCheckDebug softCheck = SoftCheckDebugInit.softCheckDebug();
        assertNotNull(softCheck, "SoftCheckDebug is null!");
        
        System.out.println("SoftCheckDebug: openSoftCheck() " + softCheck.openSoftCheck());
        
        testEnd("SoftCheckDebug", "openSoftCheck()");
    }
    
    @Test
    public void closeSoftCheck() {
        testBegin("SoftCheckDebug", "closeSoftCheck()");
        
        SoftCheckDebug softCheck = SoftCheckDebugInit.softCheckDebug();
        assertNotNull(softCheck, "SoftCheckDebug is null!");
        
        softCheck.openSoftCheck();
        System.out.println("SoftCheckDebug: closeSoftCheck() " + softCheck.closeSoftCheck());
        
        testEnd("SoftCheckDebug", "closeSoftCheck()");
    }
    
    @Test
    public void cancelSoftCheck() {
        testBegin("SoftCheckDebug", "cancelSoftCheck()");
        
        SoftCheckDebug softCheck = SoftCheckDebugInit.softCheckDebug();
        assertNotNull(softCheck, "SoftCheckDebug is null!");
        
        softCheck.openSoftCheck();
        System.out.println("SoftCheckDebug: cancelSoftCheck() " + softCheck.cancelSoftCheck());
        
        testEnd("SoftCheckDebug", "cancelSoftCheck()");
    }
}

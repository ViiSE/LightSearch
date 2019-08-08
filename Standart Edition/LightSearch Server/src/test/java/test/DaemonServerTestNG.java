/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import lightsearch.server.daemon.DaemonServer;
import lightsearch.server.daemon.DaemonServerCreator;
import lightsearch.server.daemon.DaemonServerCreatorInit;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DaemonServerTestNG {
    
    @Test
    public void exec() {
        testBegin("DaemonServer", "exec()");

        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(osDetector);
        String currentDirectory = currentServerDirectory.currentDirectory();
        assertNotNull(currentDirectory, "CurrentDirectory is null!");
        
        DaemonServerCreator daemonServerCreator = DaemonServerCreatorInit.daemonServerCreator(osDetector, currentDirectory);
        DaemonServer daemonServer = daemonServerCreator.createDaemonServer();
        assertNotNull(daemonServer, "Daemon server is null!");
        
        testEnd("DaemonServer", "exec()");
        
        daemonServer.exec();
    }
}

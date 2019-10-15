/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.producer.cmd.client;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.processor.ClientProcessor;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.time.CurrentDateTime;

public interface ProcessorClientProducer {
    ClientProcessor getAuthenticationProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier);
    ClientProcessor getSearchProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier);
    ClientProcessor getCancelSoftCheckProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier);
    ClientProcessor getCloseSoftCheckProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier);
    ClientProcessor getConfirmSoftCheckProductsProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier);
    ClientProcessor getOpenSoftCheckProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier);

//    ClientProcessor getAuthenticationProcessorDebugInstance(
//            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO);
//    ClientProcessor getSearchProcessorDebugInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
//    ClientProcessor getCancelSoftCheckProcessorDebugInstance(
//            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck);
//    ClientProcessor getCloseSoftCheckProcessorDebugInstance(
//            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck);
//    ClientProcessor getConfirmSoftCheckProductsProcessorDebugInstance(
//            LightSearchServerDTO serverDTO, LightSearchChecker checker);
//    ClientProcessor getOpenSoftCheckProcessorDebugInstance(
//            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck);

}

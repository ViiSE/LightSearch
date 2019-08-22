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

package lightsearch.server.producer.cmd.client;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.processor.ProcessorClient;
import lightsearch.server.cmd.client.processor.debug.SoftCheckDebug;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.time.CurrentDateTime;

public interface ProcessorClientProducer {
    ProcessorClient getAuthenticationProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier);
    ProcessorClient getSearchProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier);
    ProcessorClient getCancelSoftCheckProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier);
    ProcessorClient getCloseSoftCheckProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier);
    ProcessorClient getConfirmSoftCheckProductsProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier);
    ProcessorClient getOpenSoftCheckProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier);

    ProcessorClient getAuthenticationProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO);
    ProcessorClient getSearchProcessorDebugInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorClient getCancelSoftCheckProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck);
    ProcessorClient getCloseSoftCheckProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck);
    ProcessorClient getConfirmSoftCheckProductsProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorClient getOpenSoftCheckProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck);

}

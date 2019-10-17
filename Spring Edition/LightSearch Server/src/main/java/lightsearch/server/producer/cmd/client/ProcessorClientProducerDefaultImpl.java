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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("processorClientProducerDefault")
public class ProcessorClientProducerDefaultImpl implements ProcessorClientProducer {

    private final String AUTHENTICATION_PROCESSOR              = "authenticationProcessorClient";
    private final String SEARCH_PROCESSOR                      = "searchProcessorClient";
    private final String CANCEL_SOFT_CHECK_PROCESSOR           = "cancelSoftCheckProcessorClient";
    private final String CLOSE_SOFT_CHECK_PROCESSOR            = "closeSoftCheckProcessorClient";
    private final String CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR = "confirmSoftCheckProductsProcessorClient";
    private final String OPEN_SOFT_CHECK_PROCESSOR             = "openSoftCheckProcessorClient";

    private final String AUTHENTICATION_PROCESSOR_DEBUG              = "authenticationProcessorClientDebug";
    private final String SEARCH_PROCESSOR_DEBUG                      = "searchProcessorClientDebug";
    private final String CANCEL_SOFT_CHECK_PROCESSOR_DEBUG           = "cancelSoftCheckProcessorClientDebug";
    private final String CLOSE_SOFT_CHECK_PROCESSOR_DEBUG            = "closeSoftCheckProcessorClientDebug";
    private final String CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR_DEBUG = "confirmSoftCheckProductsProcessorClientDebug";
    private final String OPEN_SOFT_CHECK_PROCESSOR_DEBUG             = "openSoftCheckProcessorClientDebug";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ClientProcessor getAuthenticationProcessorInstance(
            LightSearchServerService serverDTO, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor) ctx.getBean(AUTHENTICATION_PROCESSOR, serverDTO, checker, currentDateTime,
                databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getSearchProcessorInstance(
            LightSearchServerService serverDTO, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return null;//(ClientProcessor) ctx.getBean(SEARCH_PROCESSOR, serverDTO, checker, currentDateTime,
               // databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getCancelSoftCheckProcessorInstance(
            LightSearchServerService serverDTO, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return null;//(ClientProcessor) ctx.getBean(CANCEL_SOFT_CHECK_PROCESSOR, serverDTO, checker, currentDateTime,
                //databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getCloseSoftCheckProcessorInstance(
            LightSearchServerService serverDTO, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return null;//(ClientProcessor) ctx.getBean(CLOSE_SOFT_CHECK_PROCESSOR, serverDTO, checker, currentDateTime,
                //databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getConfirmSoftCheckProductsProcessorInstance(
            LightSearchServerService serverDTO, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return null;//(ClientProcessor) ctx.getBean(CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR, serverDTO, checker, currentDateTime,
                //databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getOpenSoftCheckProcessorInstance(
            LightSearchServerService serverDTO, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return null;//(ClientProcessor) ctx.getBean(OPEN_SOFT_CHECK_PROCESSOR, serverDTO, checker, currentDateTime,
                //databaseRecordIdentifier);
    }

//    @Override
//    public ClientProcessor getAuthenticationProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker checker, ClientDAO clientDAO) {
//        return (ClientProcessor) ctx.getBean(AUTHENTICATION_PROCESSOR_DEBUG, serverDTO, checker, clientDAO);
//    }
//
//    @Override
//    public ClientProcessor getSearchProcessorDebugInstance(LightSearchServerService serverDTO, LightSearchChecker checker) {
//        return (ClientProcessor) ctx.getBean(SEARCH_PROCESSOR_DEBUG, serverDTO, checker);
//    }
//
//    @Override
//    public ClientProcessor getCancelSoftCheckProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck) {
//        return (ClientProcessor) ctx.getBean(CANCEL_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, checker, softCheck);
//    }
//
//    @Override
//    public ClientProcessor getCloseSoftCheckProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck) {
//        return (ClientProcessor) ctx.getBean(CLOSE_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, checker, softCheck);
//    }
//
//    @Override
//    public ClientProcessor getConfirmSoftCheckProductsProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker checker) {
//        return (ClientProcessor) ctx.getBean(CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR_DEBUG, serverDTO, checker);
//    }
//
//    @Override
//    public ClientProcessor getOpenSoftCheckProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck) {
//        return (ClientProcessor) ctx.getBean(OPEN_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, checker, softCheck);
//    }
}

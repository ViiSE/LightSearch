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

package lightsearch.server.producer.cmd.client.processor;

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

    private final String AUTHENTICATION_PROCESSOR              = "authenticationProcessor";
    private final String SEARCH_PROCESSOR                      = "searchProcessor";
    private final String CANCEL_SOFT_CHECK_PROCESSOR           = "cancelSoftCheckProcessor";
    private final String CLOSE_SOFT_CHECK_PROCESSOR            = "closeSoftCheckProcessor";
    private final String CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR = "confirmSoftCheckProductsProcessor";
    private final String OPEN_SOFT_CHECK_PROCESSOR             = "openSoftCheckProcessor";


    private final String AUTHENTICATION_PROCESSOR_TEST              = "authenticationProcessorTest";
    private final String SEARCH_PROCESSOR_TEST                      = "searchProcessorTest";
    private final String CANCEL_SOFT_CHECK_PROCESSOR_TEST           = "cancelSoftCheckProcessorTest";
    private final String CLOSE_SOFT_CHECK_PROCESSOR_TEST            = "closeSoftCheckProcessorTest";
    private final String CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR_TEST = "confirmSoftCheckProductsProcessorTest";
    private final String OPEN_SOFT_CHECK_PROCESSOR_TEST             = "openSoftCheckProcessorTest";

//    private final String AUTHENTICATION_PROCESSOR_DEBUG              = "authenticationProcessorClientDebug";
//    private final String SEARCH_PROCESSOR_DEBUG                      = "searchProcessorClientDebug";
//    private final String CANCEL_SOFT_CHECK_PROCESSOR_DEBUG           = "cancelSoftCheckProcessorClientDebug";
//    private final String CLOSE_SOFT_CHECK_PROCESSOR_DEBUG            = "closeSoftCheckProcessorClientDebug";
//    private final String CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR_DEBUG = "confirmSoftCheckProductsProcessorClientDebug";
//    private final String OPEN_SOFT_CHECK_PROCESSOR_DEBUG             = "openSoftCheckProcessorClientDebug";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ClientProcessor getAuthenticationProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(AUTHENTICATION_PROCESSOR, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getSearchProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(SEARCH_PROCESSOR, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getCancelSoftCheckProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(CANCEL_SOFT_CHECK_PROCESSOR, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getCloseSoftCheckProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(CLOSE_SOFT_CHECK_PROCESSOR, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getConfirmSoftCheckProductsProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getOpenSoftCheckProcessorInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(OPEN_SOFT_CHECK_PROCESSOR, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getAuthenticationProcessorTestInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(AUTHENTICATION_PROCESSOR_TEST, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getSearchProcessorTestInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(SEARCH_PROCESSOR_TEST, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getCancelSoftCheckProcessorTestInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(CANCEL_SOFT_CHECK_PROCESSOR_TEST, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getCloseSoftCheckProcessorTestInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(CLOSE_SOFT_CHECK_PROCESSOR_TEST, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getConfirmSoftCheckProductsProcessorTestInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR_TEST, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

    @Override
    public ClientProcessor getOpenSoftCheckProcessorTestInstance(
            LightSearchServerService serverService, LightSearchChecker checker,
            CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        return (ClientProcessor)
                ctx.getBean(OPEN_SOFT_CHECK_PROCESSOR_TEST, serverService, checker, currentDateTime, databaseRecordIdentifier);
    }

//    @Override
//    public ClientProcessor getAuthenticationProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker lightsearch.server.checker, ClientDAO clientDAO) {
//        return (ClientProcessor) ctx.getBean(AUTHENTICATION_PROCESSOR_DEBUG, serverDTO, lightsearch.server.checker, clientDAO);
//    }
//
//    @Override
//    public ClientProcessor getSearchProcessorDebugInstance(LightSearchServerService serverDTO, LightSearchChecker lightsearch.server.checker) {
//        return (ClientProcessor) ctx.getBean(SEARCH_PROCESSOR_DEBUG, serverDTO, lightsearch.server.checker);
//    }
//
//    @Override
//    public ClientProcessor getCancelSoftCheckProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker lightsearch.server.checker, SoftCheckDebug softCheck) {
//        return (ClientProcessor) ctx.getBean(CANCEL_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, lightsearch.server.checker, softCheck);
//    }
//
//    @Override
//    public ClientProcessor getCloseSoftCheckProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker lightsearch.server.checker, SoftCheckDebug softCheck) {
//        return (ClientProcessor) ctx.getBean(CLOSE_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, lightsearch.server.checker, softCheck);
//    }
//
//    @Override
//    public ClientProcessor getConfirmSoftCheckProductsProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker lightsearch.server.checker) {
//        return (ClientProcessor) ctx.getBean(CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR_DEBUG, serverDTO, lightsearch.server.checker);
//    }
//
//    @Override
//    public ClientProcessor getOpenSoftCheckProcessorDebugInstance(
//            LightSearchServerService serverDTO, LightSearchChecker lightsearch.server.checker, SoftCheckDebug softCheck) {
//        return (ClientProcessor) ctx.getBean(OPEN_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, lightsearch.server.checker, softCheck);
//    }
}

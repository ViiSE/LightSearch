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
import lightsearch.server.iterator.IteratorDatabaseRecord;
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
    public ProcessorClient getAuthenticationProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, IteratorDatabaseRecord iteratorDatabaseRecord) {
        return (ProcessorClient) ctx.getBean(AUTHENTICATION_PROCESSOR, serverDTO, checker, clientDAO, currentDateTime,
                iteratorDatabaseRecord);
    }

    @Override
    public ProcessorClient getSearchProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, IteratorDatabaseRecord iteratorDatabaseRecord) {
        return (ProcessorClient) ctx.getBean(SEARCH_PROCESSOR, serverDTO, checker, clientDAO, currentDateTime,
                iteratorDatabaseRecord);
    }

    @Override
    public ProcessorClient getCancelSoftCheckProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, IteratorDatabaseRecord iteratorDatabaseRecord) {
        return (ProcessorClient) ctx.getBean(CANCEL_SOFT_CHECK_PROCESSOR, serverDTO, checker, clientDAO, currentDateTime,
                iteratorDatabaseRecord);
    }

    @Override
    public ProcessorClient getCloseSoftCheckProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, IteratorDatabaseRecord iteratorDatabaseRecord) {
        return (ProcessorClient) ctx.getBean(CLOSE_SOFT_CHECK_PROCESSOR, serverDTO, checker, clientDAO, currentDateTime,
                iteratorDatabaseRecord);
    }

    @Override
    public ProcessorClient getConfirmSoftCheckProductsProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, IteratorDatabaseRecord iteratorDatabaseRecord) {
        return (ProcessorClient) ctx.getBean(CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR, serverDTO, checker, clientDAO, currentDateTime,
                iteratorDatabaseRecord);
    }

    @Override
    public ProcessorClient getOpenSoftCheckProcessorInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
            CurrentDateTime currentDateTime, IteratorDatabaseRecord iteratorDatabaseRecord) {
        return (ProcessorClient) ctx.getBean(OPEN_SOFT_CHECK_PROCESSOR, serverDTO, checker, clientDAO, currentDateTime,
                iteratorDatabaseRecord);
    }

    @Override
    public ProcessorClient getAuthenticationProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO) {
        return (ProcessorClient) ctx.getBean(AUTHENTICATION_PROCESSOR_DEBUG, serverDTO, checker, clientDAO);
    }

    @Override
    public ProcessorClient getSearchProcessorDebugInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorClient) ctx.getBean(SEARCH_PROCESSOR_DEBUG, serverDTO, checker);
    }

    @Override
    public ProcessorClient getCancelSoftCheckProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck) {
        return (ProcessorClient) ctx.getBean(CANCEL_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, checker, softCheck);
    }

    @Override
    public ProcessorClient getCloseSoftCheckProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck) {
        return (ProcessorClient) ctx.getBean(CLOSE_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, checker, softCheck);
    }

    @Override
    public ProcessorClient getConfirmSoftCheckProductsProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorClient) ctx.getBean(CONFIRM_SOFT_CHECK_PRODUCTS_PROCESSOR_DEBUG, serverDTO, checker);
    }

    @Override
    public ProcessorClient getOpenSoftCheckProcessorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck) {
        return (ProcessorClient) ctx.getBean(OPEN_SOFT_CHECK_PROCESSOR_DEBUG, serverDTO, checker, softCheck);
    }
}

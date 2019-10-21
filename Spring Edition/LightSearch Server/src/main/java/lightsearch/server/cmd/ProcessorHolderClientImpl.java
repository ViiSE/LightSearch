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

package lightsearch.server.cmd;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.processor.ClientProcessor;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.producer.checker.LightSearchCheckerProducer;
import lightsearch.server.producer.cmd.client.processor.ProcessorClientProducer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierProducer;
import lightsearch.server.producer.time.CurrentDateTimeProducer;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static lightsearch.server.cmd.client.ClientCommandEnum.*;

@Component("clientProcessorHolder")
public class ProcessorHolderClientImpl implements ProcessorHolder {

    private final Map<String, ClientProcessor> holder = new HashMap<>();

    @Autowired private ProcessorClientProducer producer;
    @Autowired private LightSearchServerService serverService;
    @Autowired private LightSearchCheckerProducer checkerProducer;
    @Autowired private CurrentDateTimeProducer currentDateTimeProducer;
    @Autowired private DatabaseRecordIdentifierProducer databaseRecordIdentifierProducer;

    @Override
    public Processor get(String command) {
        if(holder.isEmpty())
            initHolder();

        return holder.get(command);
    }

    private void initHolder() {
        LightSearchChecker checker = checkerProducer.getLightSearchCheckerDefaultInstance();
        CurrentDateTime currentDateTime = currentDateTimeProducer.getCurrentDateTimeDefaultInstance();
        DatabaseRecordIdentifier databaseRecordIdentifier =
                databaseRecordIdentifierProducer.getDatabaseRecordIdentifierDefaultInstance();

        holder.put(CONNECT.stringValue(), producer.getAuthenticationProcessorInstance(
                serverService, checker, currentDateTime, databaseRecordIdentifier));
        holder.put(SEARCH.stringValue(),  producer.getSearchProcessorInstance(
                serverService, checker, currentDateTime, databaseRecordIdentifier));
        holder.put(OPEN_SOFT_CHECK.stringValue(), producer.getOpenSoftCheckProcessorInstance
                (serverService, checker, currentDateTime, databaseRecordIdentifier));
        holder.put(CLOSE_SOFT_CHECK.stringValue(), producer.getCloseSoftCheckProcessorInstance(
                serverService, checker, currentDateTime, databaseRecordIdentifier));
        holder.put(CANCEL_SOFT_CHECK.stringValue(), producer.getCancelSoftCheckProcessorInstance(
                serverService, checker, currentDateTime, databaseRecordIdentifier));
        holder.put(CONFIRM_SOFT_CHECK_PRODUCTS.stringValue(), producer.getConfirmSoftCheckProductsProcessorInstance(
                serverService, checker, currentDateTime, databaseRecordIdentifier));
    }
}

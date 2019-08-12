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
package lightsearch.client.bot;

import lightsearch.client.bot.constants.BotSettingsEnum;
import lightsearch.client.bot.data.*;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorCloseSoftCheck;
import lightsearch.client.bot.processor.ProcessorConfirmSoftCheckProducts;
import lightsearch.client.bot.processor.ProcessorSearch;
import lightsearch.client.bot.producer.ProcessorProducer;
import lightsearch.client.bot.producer.ProductsCreatorProducer;
import lightsearch.client.bot.producer.SearchDTOCreatorProducer;
import lightsearch.client.bot.producer.TestCycleProducer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("testCycleCreatorJSON")
@Scope("prototype")
public class TestCycleCreatorJSONImpl implements TestCycleCreator {
    
    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String SEARCH_DTO     = BotSettingsEnum.SEARCH_DTO.toString();
    private final String PRODUCT_DTO    = BotSettingsEnum.PRODUCT_DTO.toString();
    private final String DELIVERY       = BotSettingsEnum.DELIVERY.toString();

    private final String PROCESSOR_SEARCH          = "lightsearch.client.bot.processor.ProcessorSearchDefaultImpl";
    private final String PROCESSOR_CONFIRM_SF_PROD = "lightsearch.client.bot.processor.ProcessorConfirmSoftCheckProductsDefaultImpl";
    private final String PROCESSOR_CLOSE_SF        = "lightsearch.client.bot.processor.ProcessorCloseSoftCheckDefaultImpl";

    private final JSONArray cycleContent;

    @Autowired private SearchDTOCreatorProducer searchDTOCreatorProducer;
    @Autowired private ProcessorProducer processorProducer;
    @Autowired private ProductsCreatorProducer productsCreatorProducer;
    @Autowired private TestCycleProducer testCycleProducer;

    public TestCycleCreatorJSONImpl(Object cycleContent) {
        this.cycleContent = (JSONArray) cycleContent;
    }

    @Override
    public TestCycle createCycle() {
        List<Processor> procs = new ArrayList<>();
        cycleContent.forEach((Object objProc) -> {
            JSONObject jProc = (JSONObject) objProc;
            String impl = jProc.get(IMPLEMENTATION).toString();
            try {
                Class clazz = Class.forName(impl);

                if(ProcessorSearch.class.isAssignableFrom(clazz)) {
                    JSONObject jSearchDTO = (JSONObject) jProc.get(SEARCH_DTO);
                    SearchDTOCreator searchDTOCreator = searchDTOCreatorProducer.getSearchDTOCreatorJSONInstance(jSearchDTO);
                    ProcessorSearch procSearch =  processorProducer.getProcessorSearchDefaultInstance(PROCESSOR_SEARCH, searchDTOCreator.createSearchDTO());
                    procs.add(procSearch);
                }
                else if(ProcessorConfirmSoftCheckProducts.class.isAssignableFrom(clazz)) {
                    JSONObject jProdDTO = (JSONObject) jProc.get(PRODUCT_DTO);
                    ProductsCreator prodCr = productsCreatorProducer.getProductsCreatorJSONInstance(jProdDTO);
                    ProcessorConfirmSoftCheckProducts procCSCProd =
                            processorProducer.getProcessorConfirmSoftCheckProductsDefaultInstance(
                                    PROCESSOR_CONFIRM_SF_PROD, prodCr.createProducts());
                    procs.add(procCSCProd);
                }
                else if(ProcessorCloseSoftCheck.class.isAssignableFrom(clazz)) {
                    String delivery = jProc.get(DELIVERY).toString();
                    ProcessorCloseSoftCheck procCloseSF = processorProducer.getProcessorCloseSoftCheckDefaultInstance(
                            PROCESSOR_CLOSE_SF, delivery);
                    procs.add(procCloseSF);
                }
                else {
                    Processor procInstance = processorProducer.getProcessorDefaultInstance(impl);
                    procs.add(procInstance);
                }
            } catch (ClassNotFoundException | SecurityException | IllegalArgumentException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });
        if(procs.isEmpty())
            throw new RuntimeException("Processors list is empty!");
        
        return testCycleProducer.getTestCycleDefaultInstance(procs);
    }    
}

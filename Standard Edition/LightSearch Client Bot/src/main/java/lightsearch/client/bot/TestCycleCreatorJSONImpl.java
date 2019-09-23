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

import lightsearch.client.bot.data.*;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorCloseSoftCheck;
import lightsearch.client.bot.processor.ProcessorConfirmSoftCheckProducts;
import lightsearch.client.bot.processor.ProcessorSearch;
import lightsearch.client.bot.settings.BotSettingsEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ViiSE
 */
public class TestCycleCreatorJSONImpl implements TestCycleCreator {
    
    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String SEARCH_DTO     = BotSettingsEnum.SEARCH_DTO.toString();
    private final String PRODUCT_DTO    = BotSettingsEnum.PRODUCT_DTO.toString();
    private final String DELIVERY       = BotSettingsEnum.DELIVERY.toString();
    
    private final JSONArray cycleContent;
    
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
                    SearchDTOCreator searchDTOCreator = 
                            SearchDTOCreatorInit.searchDTOCreator(jSearchDTO);
                    
                    Constructor constructor = clazz.getConstructor(SearchDTO.class);
                    ProcessorSearch proc = (ProcessorSearch) constructor.newInstance(
                            searchDTOCreator.createSearchDTO());
                    
                    procs.add(proc);
                }
                else if(ProcessorConfirmSoftCheckProducts.class.isAssignableFrom(clazz)) {
                    JSONObject jProdDTO = (JSONObject) jProc.get(PRODUCT_DTO);
                    ProductsCreator prodCr = ProductsCreatorInit.productsCreator(jProdDTO);
                    
                    Constructor constructor = clazz.getConstructor(List.class);
                    ProcessorConfirmSoftCheckProducts proc = 
                            (ProcessorConfirmSoftCheckProducts) constructor.newInstance(
                                    prodCr.createProducts());
                    
                    procs.add(proc);
                }
                else if(ProcessorCloseSoftCheck.class.isAssignableFrom(clazz)) {
                    String delivery = jProc.get(DELIVERY).toString();
                    
                    Constructor constructor = clazz.getConstructor(String.class);
                    ProcessorCloseSoftCheck proc = 
                            (ProcessorCloseSoftCheck) constructor.newInstance(delivery);
                    
                    procs.add(proc);
                }
                else {
                    Processor procInstance = (Processor) Class.forName(impl).newInstance();
                    procs.add(procInstance);
                }
            } catch (ClassNotFoundException | InstantiationException   | 
                     IllegalAccessException | NoSuchMethodException    | 
                     SecurityException      | IllegalArgumentException | 
                     InvocationTargetException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });
        if(procs.isEmpty())
            throw new RuntimeException("Processors list is empty!");
        
        return TestCycleInit.testCycle(procs);
    }    
}

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

package lightsearch.client.bot.producer;

import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.data.SearchDTO;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorCloseSoftCheck;
import lightsearch.client.bot.processor.ProcessorConfirmSoftCheckProducts;
import lightsearch.client.bot.processor.ProcessorSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("processorProducerDefault")
public class ProcessorProducerDefaultImpl implements ProcessorProducer {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public Processor getProcessorDefaultInstance(String beanName) {
        return ctx.getBean(beanName, Processor.class);
    }

    @Override
    public ProcessorCloseSoftCheck getProcessorCloseSoftCheckDefaultInstance(String beanName, String delivery) {
        return (ProcessorCloseSoftCheck) ctx.getBean(beanName, delivery);
    }

    @Override
    public ProcessorConfirmSoftCheckProducts getProcessorConfirmSoftCheckProductsDefaultInstance(String beanName, List<ProductDTO> products) {
        return (ProcessorConfirmSoftCheckProducts) ctx.getBean(beanName, products);
    }

    @Override
    public ProcessorSearch getProcessorSearchDefaultInstance(String beanName, SearchDTO searchDTO) {
        return (ProcessorSearch) ctx.getBean(beanName, searchDTO);
    }
}

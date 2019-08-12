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

import java.util.List;

public interface ProcessorProducer {
    Processor getProcessorDefaultInstance(String beanName);
    ProcessorCloseSoftCheck getProcessorCloseSoftCheckDefaultInstance(String beanName, String delivery);
    ProcessorConfirmSoftCheckProducts getProcessorConfirmSoftCheckProductsDefaultInstance(String beanName, List<ProductDTO> products);
    ProcessorSearch getProcessorSearchDefaultInstance(String beanName, SearchDTO searchDTO);
}

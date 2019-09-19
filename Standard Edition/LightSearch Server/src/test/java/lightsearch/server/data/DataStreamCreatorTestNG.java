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
package lightsearch.server.data;

import lightsearch.server.data.stream.DataStreamCreator;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DataStreamCreatorTestNG {
    
    @Test
    public void createDataStream() {
        testBegin("DataStreamCreator", "createDataStream()");
        
        DataStreamCreator dataStreamCreator = DataProviderCreator.createDataProvider(DataStreamCreator.class);
        assertNotNull(dataStreamCreator, "DataStreamCreator is null!");
        System.out.println("dataStreamCreator: " + dataStreamCreator);
    
        testEnd("DataStreamCreator", "createDataStream()");
    }
    
    @Test
    public void dataInputStream() {
        testBegin("DataStreamCreator", "dataInputStream()");

        DataStreamCreator dataStreamCreator = DataProviderCreator.createDataProvider(DataStreamCreator.class);
        assertNotNull(dataStreamCreator, "DataStreamCreator is null!");
        System.out.println("dataInputStream: " + dataStreamCreator.dataInputStream());
        
        testEnd("DataStreamCreator", "dataInputStream()");
    }
    
    @Test
    public void dataOutputStream() {
        testBegin("DataStreamCreator", "dataOutputStream()");

        DataStreamCreator dataStreamCreator = DataProviderCreator.createDataProvider(DataStreamCreator.class);
        assertNotNull(dataStreamCreator, "DataStreamCreator is null!");
        System.out.println("dataOutputStream: " + dataStreamCreator.dataOutputStream());
        
        testEnd("DataStreamCreator", "dataOutputStream()");
    }
}

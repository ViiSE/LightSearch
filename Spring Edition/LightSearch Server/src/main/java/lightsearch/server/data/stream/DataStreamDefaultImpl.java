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
package lightsearch.server.data.stream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author ViiSE
 */
@Component("dataStreamDefault")
@Scope("prototype")
public class DataStreamDefaultImpl implements DataStream {
    
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    
    public DataStreamDefaultImpl(DataStreamCreator dataStreamCreator) {
        dataInputStream = dataStreamCreator.dataInputStream();
        dataOutputStream = dataStreamCreator.dataOutputStream();
    }
    
    @Override
    public DataInputStream dataInputStream() {
        return dataInputStream;
    }

    @Override
    public DataOutputStream dataOutputStream() {
        return dataOutputStream;
    }
}

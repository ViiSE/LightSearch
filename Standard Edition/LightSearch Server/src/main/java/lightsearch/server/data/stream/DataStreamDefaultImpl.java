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

import java.io.*;

/**
 * Реализация интерфейса {@link lightsearch.server.data.stream.DataStream} по умолчанию.
 * <p>
 * Перед тем, как создавать экземпляр этого класса, необходимо в экземпляре
 * {@link lightsearch.server.data.stream.DataStreamCreator} вызвать метод {@link DataStreamCreator#createDataStream()}
 * для создания потока данных.
 * @author ViiSE
 * @since 2.0.0
 */
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

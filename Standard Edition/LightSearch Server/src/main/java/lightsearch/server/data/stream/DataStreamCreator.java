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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import lightsearch.server.exception.DataStreamCreatorException;

/**
 * Создает поток данных {@link lightsearch.server.data.stream.DataStream} при подключении клиента к LightSearch Server.
 * <p>
 * Если поток данных не удалось создать, то генерируется исключение
 * {@link lightsearch.server.exception.DataStreamCreatorException}.
 * @author ViiSE
 * @see java.io.DataInputStream
 * @see java.io.DataOutputStream
 * @since 1.0
 */
public interface DataStreamCreator {
    void createDataStream() throws DataStreamCreatorException;
    DataInputStream dataInputStream();
    DataOutputStream dataOutputStream();
}

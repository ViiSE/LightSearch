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
package lightsearch.server.initialization;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Реализация интерфейса {@link lightsearch.server.initialization.ServerSettings} по умолчанию.
 * <p>
 * Настройки LightSearch Server считываются из файла {@code settings} в директории, в которой расположен исполняемый
 * jar-файл LightSearch Server. В файле {@code settings} разделителем настроек является символ {@code ;}.
 * <p>
 * Если файл {@code settings} не был найден, если значение перезагрузки LightSearch Server или значение тайм-аута клиента
 * со стороны сервера меньше нуля, то сгенерируется исключение {@link java.lang.RuntimeException}.
 * Значение перезагрузки LightSearch Server равное нулю расценивается как работа LightSearch Server без перезагрузки.
 * Значение тайм-аута клиента со стороны сервера равное нулю расценивается как работа LightSearch Server без тайм-аута
 * клиента со стороны сервера.
 * <p>
 * Для определения текущей директории используется интерфейс
 * {@link lightsearch.server.initialization.CurrentServerDirectory}.
 * @author ViiSE
 * @since 1.0
 */
public class ServerSettingsFromFileDefaultImpl implements ServerSettings{

    private final CurrentServerDirectory currentServerDirectory;
    
    public ServerSettingsFromFileDefaultImpl(CurrentServerDirectory currentServerDirectory) {
        this.currentServerDirectory = currentServerDirectory;
    }
    
    @Override
    public int rebootServerValue() {
        int serverReboot;
        String currentDirectory = currentServerDirectory.currentDirectory();
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "settings")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            String serverRebootString = "";
            for (int i = 0; i < buffer.length; i++) {
                if ((char)buffer[i] == ';') {
                    break;
                }
                if (i == 0) serverRebootString += (char)buffer[i];
            }
            serverReboot = Integer.parseInt(serverRebootString);
            if(serverReboot < 0)
                throw new RuntimeException("Server reboot value is less than 0!");
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return serverReboot;
    }

    @Override
    public int timeoutClientValue() {
        int clientTimeout;
        String currentDirectory = currentServerDirectory.currentDirectory();
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "settings")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            StringBuilder clientTimeoutString = new StringBuilder();
            int count = 0;
            for(int i = 0; i < buffer.length; i++) {
                if((char)buffer[i] == ';') {
                    count++;
                    i++;
                }
                if(count == 1) clientTimeoutString.append((char) buffer[i]);
            }

            clientTimeout = Integer.parseInt(clientTimeoutString.toString());
            if(clientTimeout < 0)
                throw new RuntimeException("Client timeout value is less than 0!");
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        return clientTimeout;
    }
    
}

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
 * Реализация интерфейса {@link lightsearch.server.initialization.ServerPort} по умолчанию.
 * <p>
 * Порт сервера считывается из файла {@code ini_port} в директории, в которой расположен исполняемый jar-файл
 * LightSearch Server.
 * <p>
 * Если файл {@code ini_port} не был найден, то сгенерируется исключение {@link java.lang.RuntimeException}.
 * <p>
 * Для определения текущей директории используется интерфейс
 * {@link lightsearch.server.initialization.CurrentServerDirectory}.
 * @since 1.0
 * @author ViiSE
 */
public class ServerPortFromFileDefaultImpl implements ServerPort {

    private final CurrentServerDirectory currentServerDirectory;
    
    public ServerPortFromFileDefaultImpl(CurrentServerDirectory currentServerDirectory) {
        this.currentServerDirectory = currentServerDirectory;
    }
    
    @Override
    public int port() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        
        int port = 0;
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "ini_port")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            String portString = new String();
            for(int i = 0; i < buffer.length; i++)
                portString += (char)buffer[i];
            
            port = Integer.parseInt(portString);
            
            if(port < 1023 || port > 65535)
                throw new RuntimeException("Wrong port number!");
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return port;
    }
    
}

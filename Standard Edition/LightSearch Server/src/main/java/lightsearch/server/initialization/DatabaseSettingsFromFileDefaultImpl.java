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
 * Реализация интерфейса {@link lightsearch.server.initialization.DatabaseSettings} по умолчанию.
 * <p>
 * Настройки базы данных считываются из файла {@code db} в директории, в которой расположен исполняемый jar-файл
 * LightSearch Server. В файле {@code admins} разделителем между настройками является символ {@code ;}.
 * <p>
 * Если файл {@code db} не был найден, то сгенерируется исключение {@link java.lang.RuntimeException}.
 * <p>
 * Для определения текущей директории используется интерфейс
 * {@link lightsearch.server.initialization.CurrentServerDirectory}.
 * @author ViiSE
 * @since 1.0.0
 */
public class DatabaseSettingsFromFileDefaultImpl implements DatabaseSettings {
    
    private final CurrentServerDirectory currentServerDirectory;
    
    public DatabaseSettingsFromFileDefaultImpl(CurrentServerDirectory currentServerDirectory) {
        this.currentServerDirectory = currentServerDirectory;
    }

    @Override
    public String name() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        StringBuilder dbName = new StringBuilder();
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "db")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            int count = 0;
            for(int i = 0; i < buffer.length; i++) {
                if((char)buffer[i] == ';') {
                    count++;
                    i++;
                }
                if(count == 2) dbName.append((char) buffer[i]);
            }
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return dbName.toString();
    }

    @Override
    public String ip() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        StringBuilder dbIP = new StringBuilder();
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "db")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            int count = 0;
            for(int i = 0; i < buffer.length; i++) {
                if((char)buffer[i] == ';') {
                    count++;
                    i++;
                }
                if(count == 0) dbIP.append((char) buffer[i]);
            }
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return dbIP.toString();
    }

    @Override
    public int port() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        int dbPort;
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "db")) {
            StringBuilder dbPortStr = new StringBuilder();
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            int count = 0;
            for(int i = 0; i < buffer.length; i++) {
                if((char)buffer[i] == ';') {
                    count++;
                    i++;
                }
                if(count == 1) dbPortStr.append((char) buffer[i]);
            }
            dbPort = Integer.parseInt(dbPortStr.toString());
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return dbPort;
    }
}

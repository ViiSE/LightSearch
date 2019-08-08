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
 *
 * @author ViiSE
 */
public class DatabaseSettingsFromFileDefaultImpl implements DatabaseSettings {
    
    private final CurrentServerDirectory currentServerDirectory;
    
    public DatabaseSettingsFromFileDefaultImpl(CurrentServerDirectory currentServerDirectory) {
        this.currentServerDirectory = currentServerDirectory;
    }

    @Override
    public String name() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        String dbName  = new String();
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "db")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            int count = 0;
            for(int i = 0; i < buffer.length; i++) {
                if((char)buffer[i] == ';') {
                    count++;
                    i++;
                }
                if(count == 2) dbName += (char)buffer[i];
            }
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return dbName;
    }

    @Override
    public String ip() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        String dbIP  = new String();
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "db")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            int count = 0;
            for(int i = 0; i < buffer.length; i++) {
                if((char)buffer[i] == ';') {
                    count++;
                    i++;
                }
                if(count == 0) dbIP += (char)buffer[i];
            }
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return dbIP;
    }

    @Override
    public int port() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        int dbPort  = 0;
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "db")) {
            String dbPortStr = new String();
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            int count = 0;
            for(int i = 0; i < buffer.length; i++) {
                if((char)buffer[i] == ';') {
                    count++;
                    i++;
                }
                if(count == 1) dbPortStr += (char)buffer[i];
            }
            dbPort = Integer.parseInt(dbPortStr);
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return dbPort;
    }
}

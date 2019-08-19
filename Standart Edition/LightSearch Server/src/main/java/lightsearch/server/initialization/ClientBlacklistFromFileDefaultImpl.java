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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Реализация интерфейса {@link lightsearch.server.initialization.ClientBlacklist} по умолчанию.
 * <p>
 * Черный список считывается из файла {@code blacklist} в директории, в которой расположен исполняемый jar-файл
 * LightSearch Server. В файле {@code blacklist} каждое значение IMEI пишется с новой строки.
 * <p>
 * Если файл {@code blacklist} не был найден, то произойдет попытка создания файла с именем {@code blacklist}.
 * <p>
 * Если файл {@code blacklist} не будет создан, то сгенерируется исключение {@link java.lang.RuntimeException}.
 * <p>
 * Для определения текущей директории используется интерфейс
 * {@link lightsearch.server.initialization.CurrentServerDirectory}.
 * @since 1.0
 * @author ViiSE
 */
public class ClientBlacklistFromFileDefaultImpl implements ClientBlacklist {

    private final CurrentServerDirectory currentDirectory;

    public ClientBlacklistFromFileDefaultImpl(CurrentServerDirectory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    @Override
    public ArrayList<String> blacklist() {

        String currDir = this.currentDirectory.currentDirectory();
        ArrayList<String> blacklist = new ArrayList<>();
        
        try(FileInputStream fin = new FileInputStream(currDir + "blacklist"); 
                BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            String strLine;
            while ((strLine = br.readLine()) != null){
                blacklist.add(strLine);
            }
        }
        catch(IOException ex) {
            System.out.println();
            System.out.println("Error blacklist file: " + ex.getMessage());
            System.out.println("Create new blacklist file...");
            try {
                File blacklistFile = new File(currDir + "blacklist");
                blacklistFile.createNewFile();
            }
            catch(IOException ioEx) {
                throw new RuntimeException("Error: " + ioEx.getMessage());
            }
        }
        return blacklist;
    }   
}

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
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lightsearch.server.security.HashAlgorithms;
import lightsearch.server.security.HashAlgorithmsInit;

/**
 * Реализация интерфейса {@link lightsearch.server.initialization.AdministratorsMap} по умолчанию.
 * <p>
 * Карта администраторов считывается из файла {@code admins} в директории, в которой расположен исполняемый jar-файл
 * LightSearch Server. В файле {@code admins} разделителем ключа и значения является символ {@code ;}, а каждая пара
 * ключ-значение пишется с новой строки.
 * <p>
 * Если файл {@code admins} не был найден, то произойдет попытка создания файла с именем {@code admins}. Затем будет
 * предложено ввести пароль для администратора с именем {@code admin}.
 * <p>
 * Если файл {@code admins} пустой, то произойдет попытка создания администратора с именем {@code admin}, после чего
 * будет предложено ввести пароль для этого администратора.
 * <p>
 * Если какой-либо из сценариев выше не сработает, то сгенерируется исключение {@link java.lang.RuntimeException}.
 * <p>
 * Для определения текущей директории используется интерфейс
 * {@link lightsearch.server.initialization.CurrentServerDirectory}.
 * @since 1.0
 * @author ViiSE
 */
public class AdministratorsMapFromFileDefaultImpl implements AdministratorsMap {

    private final CurrentServerDirectory currentServerDirectory;
    
    public AdministratorsMapFromFileDefaultImpl(CurrentServerDirectory currentServerDirectory) {
        this.currentServerDirectory = currentServerDirectory;
    }
    
    @Override
    public Map<String, String> administratorsMap() {
        String currentDirectory = currentServerDirectory.currentDirectory();
        HashMap<String, String> adminMap = new HashMap<>();
        
        try(FileInputStream fin = new FileInputStream(currentDirectory + "admins"); 
                BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            String strLine;
            String name;
            String pass;
            
            while ((strLine = br.readLine()) != null){
                name = strLine.substring(0, strLine.indexOf(";"));
                pass = strLine.substring(strLine.indexOf(";")+1, strLine.length());
                adminMap.put(name, pass);
            }
            
            if(adminMap.isEmpty())
                adminMap = createAdmin(currentDirectory);
        }
        catch(IOException ex) {
            adminMap = createAdmin(currentDirectory, ex.getMessage());
        }
        return adminMap;
    }
    
    private HashMap<String, String> createAdmin(String currentDirectory) {
        HashMap<String, String> adminMap = new HashMap<>();
        
        System.out.println();
        System.out.println("File admins is empty!");
        System.out.println("Create admins file...");
        try(FileOutputStream fout = new FileOutputStream(currentDirectory + "admins")) {
           System.out.print("Input password for admin: ");
           char[] passArr = System.console().readPassword();
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                HashAlgorithms hashAlgorithms = HashAlgorithmsInit.hashAlgorithms();
                String pass = hashAlgorithms.sha256(Arrays.toString(passArr));
                bw.write("admin;" + pass);
                bw.newLine();

                adminMap.put("admin", pass);
                System.out.println("Administrator admin created successfully!");
            }
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        return adminMap;
    }
    
    private HashMap<String, String> createAdmin(String currentDirectory,
            String IOExceptionMessage) {
        HashMap<String, String> adminMap = new HashMap<>();
        
        System.out.println();
        System.out.println("Error: " + IOExceptionMessage);
        System.out.println("Create admins file...");
        try(FileOutputStream fout = new FileOutputStream(currentDirectory + "admins")) {
           System.out.print("Input password for admin: ");
           char[] passArr = System.console().readPassword();
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                HashAlgorithms hashAlgorithms = HashAlgorithmsInit.hashAlgorithms();
                String pass = hashAlgorithms.sha256(Arrays.toString(passArr));
                bw.write("admin;" + pass);
                bw.newLine();

                adminMap.put("admin", pass);
                System.out.println("Administrator admin created successfully!");
            }
        }
        catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        return adminMap;
    }
}

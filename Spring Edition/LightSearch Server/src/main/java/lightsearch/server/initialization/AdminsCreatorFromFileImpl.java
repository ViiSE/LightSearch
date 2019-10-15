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

import lightsearch.server.data.AdminsService;
import lightsearch.server.producer.security.HashAlgorithmsProducer;
import lightsearch.server.security.HashAlgorithms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;

@Component("adminsCreatorFromFile")
public class AdminsCreatorFromFileImpl implements AdminsCreator {

    private final CurrentServerDirectory currentServerDirectory;
    private final AdminsService adminsService;

    @Autowired
    private HashAlgorithmsProducer hashAlProducer;

    public AdminsCreatorFromFileImpl(CurrentServerDirectory currentServerDirectory, AdminsService adminsService) {
        this.currentServerDirectory = currentServerDirectory;
        this.adminsService = adminsService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void createAdmins() {
        String currentDirectory = currentServerDirectory.currentDirectory();

        try(FileInputStream fin = new FileInputStream(currentDirectory + "admins");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            String strLine;
            String name;
            String pass;

            while ((strLine = br.readLine()) != null) {
                name = strLine.substring(0, strLine.indexOf(";"));
                pass = strLine.substring(strLine.indexOf(";")+1);
                adminsService.admins().put(name, pass);
            }

            if(adminsService.admins().isEmpty())
                createAdmin(currentDirectory, adminsService, "File admins is empty!");
        } catch(IOException ex) {
            createAdmin(currentDirectory, adminsService, ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void createAdmin(String currentDirectory, AdminsService adminsService, String errorMessage) {
        System.out.println();
        System.out.println(errorMessage);
        System.out.println("Create admins file...");
        try(FileOutputStream fout = new FileOutputStream(currentDirectory + "admins")) {
            System.out.print("Input password for admin: ");
            char[] passArr = System.console().readPassword();
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                HashAlgorithms hashAlgorithms = hashAlProducer.getHashAlgorithmsDefaultInstance();
                String pass = hashAlgorithms.sha256(Arrays.toString(passArr));
                bw.write("admin;" + pass);
                bw.newLine();

                adminsService.admins().put("admin", pass);
                System.out.println("Administrator admin created successfully!");
            }
        } catch(IOException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
    }
}

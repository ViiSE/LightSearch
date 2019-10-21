/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.data.pojo.LightSearchSettings;
import lightsearch.server.security.HashAlgorithmsDefaultImpl;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SandBox {

    /* ~Welcome to the SandBox! You can do whatever you want here. This is experiments zone. Enjoy!~ */

    @Test
    public void sandBox() throws JsonProcessingException, IOException {
        ClientCommandDTO clientCommandDTO = new ClientCommandDTO();
        clientCommandDTO.setUserIdentifier("655");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Serialization: " + mapper.writeValueAsString(clientCommandDTO));

        ClientCommandDTO r = mapper.readValue("{\"ident\":25}", ClientCommandDTO.class);
        System.out.println("Deserialization: " + r.getUserIdentifier());

        LocalTime time = LocalTime.now();
        System.out.println("Time now: " + time);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LightSearchSettings settings = objectMapper.readValue(new File("settings"), LightSearchSettings.class);

        System.out.println("Reboot: isBefore: " + LightSearchSettings.getRebootTime().isBefore(time));
        System.out.println("Reboot: isAfter: " + LightSearchSettings.getRebootTime().isAfter(time));

        System.out.println("frequency: " + LightSearchSettings.getFrequency());

        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);

        System.out.println("Tommorow: " + today.plusDays(1));

        String hash = new HashAlgorithmsDefaultImpl().sha256("321");
        System.out.println(hash);
    }
}

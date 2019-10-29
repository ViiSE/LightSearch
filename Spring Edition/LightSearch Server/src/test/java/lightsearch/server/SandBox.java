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
import lightsearch.server.data.pojo.*;
import lightsearch.server.security.HashAlgorithmsDefaultImpl;
import lightsearch.server.time.TimeUtils;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SandBox extends AbstractTestNGSpringContextTests {

    /* ~Welcome to the SandBox! You can do whatever you want here. This is experiments zone. Enjoy!~ */

    @Test
    public void sandBox() throws JsonProcessingException, IOException {
        ClientCommandDTO clientCommandDTO = new ClientCommandDTO();
        clientCommandDTO.setUserIdentifier("655");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Serialization: " + mapper.writeValueAsString(clientCommandDTO));

        ClientCommandDTO r = mapper.readValue("{\"ident\":25}", ClientCommandDTO.class);
        System.out.println("Deserialization: " + r.getUserIdentifier());

        AdminCommandDTO adminCommandDTO = new AdminCommandDTO();
        adminCommandDTO.setCommand("change_db");
        adminCommandDTO.setPort(8080);
        adminCommandDTO.setClientTimeout(30);
        adminCommandDTO.setRestartTime("22:05");
        System.out.println("Serialization ADMIN: " + mapper.writeValueAsString(adminCommandDTO));

        AdminCommandDTO ar = mapper.readValue("{\"port\":\"8080\"}", AdminCommandDTO.class);
        System.out.println("Deserialization ADMIN: " + ar.getPort());

        AdminCommandResult admCmdRes = new AdminCommandResult("admin", "True", "SPAM EGG!",
                new ArrayList<>(){{
                    add("111111111111111");
                    add("222222222222222");
                    add("333333333333333");
                }},
                new ArrayList<>(){{
                    add(new Client("444444444444444", "client 1"));
                    add(new Client("555555555555555", "client 2"));
                    add(new Client("666666666666666", "client 3"));
                }});
        System.out.println("AdminCommand: SERIALIZATION: " + mapper.writeValueAsString(admCmdRes));

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

        System.out.println("Tomorrow: " + today.plusDays(1));

        String hash = new HashAlgorithmsDefaultImpl().sha256("321");
        System.out.println(hash);

        String correctDateTime = TimeUtils.correctDateTimeInStandardFormWithMs("2019-10-23 17:00:45.45");
        System.out.println("Correct Date: " + correctDateTime);
    }
}

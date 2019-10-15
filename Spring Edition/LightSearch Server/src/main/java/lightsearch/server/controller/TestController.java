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

package lightsearch.server.controller;

import lightsearch.server.data.pojo.AdminCommandDTO;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.data.pojo.ClientCommandResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

//    @RequestMapping(value = "/admin", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ClientCommandResult testAdminCommandDTO(AdminCommandDTO adminCommandDTO) {
//        System.out.println("serv time: " + adminCommandDTO.getServerTime());
//        ClientCommandResult clientCommandResult = new ClientCommandResult();
//        clientCommandResult.setMessage("OK");
//        clientCommandResult.setIsDone("True");
//        return clientCommandResult;
//    }
//
//    @RequestMapping(value = "/client", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ClientCommandResult testClientCommandDTO(ClientCommandDTO clientCommandDTO) {
//        System.out.println("Card code: " + clientCommandDTO.getCardCode());
//        System.out.println("Ident: " + clientCommandDTO.getUserIdentifier());
//        System.out.println("Sklad: " + clientCommandDTO.getSklad());
//        System.out.println("TK: " + clientCommandDTO.getTK());
//        System.out.println("IMEI: " + clientCommandDTO.getIMEI());
//
//        ClientCommandResult clientCommandResult = new ClientCommandResult();
//        clientCommandResult.setMessage("Done!");
//        clientCommandResult.setIsDone(clientCommandDTO.getCardCode());
//        return clientCommandResult;
//    }
//
//    @RequestMapping(value = "/client/post", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ClientCommandResult testClientCommandDTOPOST(@RequestBody ClientCommandDTO clientCommandDTO) {
//        System.out.println("Commmand: " + clientCommandDTO.getCommand());
//        System.out.println("IMEI: " + clientCommandDTO.getIMEI());
//        System.out.println("Card code: " + clientCommandDTO.getCardCode());
//        System.out.println("Ident: " + clientCommandDTO.getUserIdentifier());
//        System.out.println("os: " + clientCommandDTO.getOs());
//        System.out.println("model: " + clientCommandDTO.getModel());
//        System.out.println("username: " + clientCommandDTO.getUsername());
//        System.out.println("password: " + clientCommandDTO.getPassword());
//        System.out.println("TK: " + clientCommandDTO.getTK());
//
//        ClientCommandResult clientCommandResult = new ClientCommandResult();
//        clientCommandResult.setMessage("Done!");
//        clientCommandResult.setIsDone("True");
//        return clientCommandResult;
//    }
//
//    @RequestMapping(value = "/client/post/body", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ClientCommandResult testClientCommandDTOPOSTBody(@RequestBody String body) {
//        System.out.println("body:" + body);
//
//        ClientCommandResult clientCommandResult = new ClientCommandResult();
//        clientCommandResult.setMessage("Done!");
//        clientCommandResult.setIsDone("True");
//        return clientCommandResult;
//    }
}

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
package lightsearch.admin.panel.cmd.admin.result;

import java.util.Map;
import lightsearch.admin.panel.cmd.result.CommandResultEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class ClientListResultJSONDefaultImpl implements ClientListResult {

    private final String IMEI     = CommandResultEnum.IMEI.stringValue();
    private final String USERNAME = CommandResultEnum.USERNAME.stringValue();
    
    private final JSONArray data;
    private final Map<String, String> clients;
    
    public ClientListResultJSONDefaultImpl(Object data, Map<String, String> clients) {
        this.data = (JSONArray)data;
        this.clients = clients;
    }

    @Override
    public String result() {
        String res = "";
        clients.clear();
        res += "+================================================+\n";
        res += "|         IMEI          |        username        |\n";
        res += "+================================================+\n";
        int clientsCount = 1;
        if(data != null) {
            for (Object client : data) {
                JSONObject clientJSON = (JSONObject)client;
                res += "|" + clientsCount + ". " + clientJSON.get(IMEI).toString()
                        + "     |" + clientJSON.get(USERNAME).toString();
                int spaceCount = 24 - clientJSON.get(USERNAME).toString().length();
                for(int i = 0; i < spaceCount; i++)
                    res += " ";
                res += "|\n";
                res += "+------------------------------------------------+\n";
                clients.put(String.valueOf(clientsCount), clientJSON.get(IMEI).toString());
                clientsCount++;
            }
            res += "Clients connected: " + data.size() + "\n";
        }
        else
            res += "Clients connected: 0\n";
        
        return res;
    }
}

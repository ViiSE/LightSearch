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

package lightsearch.server.data.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCommandResult {

    private final boolean isDone;
    private final String message;
    private final List<String> blacklist;
    private final List<Client> clients;

    @JsonCreator
    public AdminCommandResult(
            @JsonProperty("isDone")   String isDone,
            @JsonProperty("message")   String message,
            @JsonProperty("blacklist") List<String> blacklist,
            @JsonProperty("clients")   List<Client> clients) {
        if(isDone.equalsIgnoreCase("true"))
            this.isDone = true;
        else if(isDone.equalsIgnoreCase("false"))
            this.isDone = false;
        else
            this.isDone = false;
        this.message = message;
        this.blacklist = blacklist;
        this.clients = clients;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public List<Client> getClients() {
        return clients;
    }
}

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

package lightsearch.server.data.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lightsearch.server.cmd.result.CommandResult;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientCommandResult implements CommandResult {

    @JsonProperty("IMEI") private final String IMEI;
    private final String isDone;
    private final String message;
    @JsonProperty("ident") private final String userIdentifier;
    @JsonProperty("TK_list") private final List<String> TKList;
    @JsonProperty("sklad_list") private final List<String> skladList;
    @JsonProperty("data") private final List<Product> data;

    @JsonCreator
    public ClientCommandResult(
            @JsonProperty("IMEI")       String IMEI,
            @JsonProperty("is_done")    String isDone,
            @JsonProperty("message")    String message,
            @JsonProperty("ident")      String userIdentifier,
            @JsonProperty("TK_list")    List<String> TKList,
            @JsonProperty("sklad_list") List<String> skladList,
            @JsonProperty("data")       List<Product> data) {
        this.IMEI = IMEI;
        this.isDone = isDone;
        this.message = message;
        this.userIdentifier = userIdentifier;
        this.TKList = TKList;
        this.skladList = skladList;
        this.data = data;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getIsDone() {
        return isDone;
    }

    public String getMessage() {
        return message;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public List<String> getTKList() {
        return TKList;
    }

    public List<String> getSkladList() {
        return skladList;
    }

    public List<Product> getData() {
        return data;
    }
}

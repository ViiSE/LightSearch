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

package lightsearch.server.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientCommandResult {

    private final String isDone;
    private final String message;
    @JsonProperty("ident") private final String userIdentifier;
    private final List<String> TKList;
    private final List<String> skladList;
    private final List<ProductDTO> data;

    @JsonCreator
    public ClientCommandResult(
            @JsonProperty("is_done")    String isDone,
            @JsonProperty("message")    String message,
            @JsonProperty("ident")      String userIdentifier,
            @JsonProperty("tk_list")    List<String> TKList,
            @JsonProperty("sklad_list") List<String> skladList,
            @JsonProperty("data")       List<ProductDTO> data) {
        this.isDone = isDone;
        this.message = message;
        this.userIdentifier = userIdentifier;
        this.TKList = TKList;
        this.skladList = skladList;
        this.data = data;
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

    public List<ProductDTO> getData() {
        return data;
    }
}

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @JsonIgnore private static final int TIMEOUT_LIMIT = 1800;
    @JsonIgnore private int timeoutLimitSeconds = TIMEOUT_LIMIT;

    @JsonProperty("IMEI") private String IMEI;
    private String username;

    public Client(@JsonProperty("IMEI") String IMEI, String username) {
        this.IMEI = IMEI;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @JsonProperty("IMEI")
    public String getIMEI() {
        return IMEI;
    }

    public int getTimeoutLimitSeconds() {
        return timeoutLimitSeconds;
    }

    public void setTimeoutLimitSeconds(int timeoutLimitSeconds) {
        this.timeoutLimitSeconds = timeoutLimitSeconds;
    }

    public void refreshTimeoutValue() {
        timeoutLimitSeconds = TIMEOUT_LIMIT;
    }

    public void decreaseTimeoutLimitValue(int decreaseValueSeconds) {
        if(decreaseValueSeconds > timeoutLimitSeconds)
            timeoutLimitSeconds = 0;
        timeoutLimitSeconds -= decreaseValueSeconds;
    }
}
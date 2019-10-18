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

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class LightSearchSettings {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private static LocalTime rebootTime;
    private static long timeoutClient;

    public void setRebootTime(LocalTime rebootTime) {
        LightSearchSettings.rebootTime = rebootTime;
    }

    public static LocalTime getRebootTime() {
        return rebootTime;
    }

    public void setTimeoutClient(long timeoutClient) {
        LightSearchSettings.timeoutClient = timeoutClient;
    }

    public static long getTimeoutClient() {
        return timeoutClient;
    }

    @Override
    public String toString() {
        return "{\"rebootTime\":\"" + rebootTime + "\",\"timeoutClient\":\"" + timeoutClient + "\"}";
    }
}

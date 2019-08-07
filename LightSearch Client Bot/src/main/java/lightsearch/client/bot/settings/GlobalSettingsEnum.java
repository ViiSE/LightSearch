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
package lightsearch.client.bot.settings;

/**
 *
 * @author ViiSE
 */
public enum GlobalSettingsEnum {
    
    SERVER_IP {
        @Override
        public String toString() { return "server_ip"; }
    },
    SERVER_PORT {
        @Override
        public String toString() { return "server_port"; }
    },
    DELAY_MESSAGE_DISPLAY {
        @Override
        public String toString() { return "delay_message_display"; }
    }
}

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
package lightsearch.server.cmd.admin;

/**
 * Названия команд администратора LightSearch Server согласно протоколу.
 * <p>
 * Ознакомиться с протоколом можно по
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Documents/Protocol/LightSearch%20Protocol.txt">ссылке</a>.
 * @author ViiSE
 * @since 1.0.0
 */
public enum AdminCommandEnum {
    CONNECT {
        @Override
        public String stringValue() {return "connect";}
    },
    RESTART {
        @Override
        public String stringValue() {return "restart";}
    },
    TIMEOUT_SERVER {
        @Override
        public String stringValue() {return "toutServer";}
    },
    TIMEOUT_CLIENT {
        @Override
        public String stringValue() {return "toutClient";}
    },
    CLIENT_LIST {
        @Override
        public String stringValue() {return "clList";}
    },
    KICK {
        @Override
        public String stringValue() {return "kick";}
    },
    BLACKLIST {
        @Override
        public String stringValue() {return "blList";}
    },
    ADD_BLACKLIST {
        @Override
        public String stringValue() {return "addBlacklist";}
    },
    DEL_BLACKLIST {
        @Override
        public String stringValue() {return "delBlacklist";}
    },
    CREATE_ADMIN {
        @Override
        public String stringValue() {return "createAdmin";}
    },
    CHANGE_DATABASE {
        @Override
        public String stringValue() {return "chDb";}
    };
    
    public abstract String stringValue();
}

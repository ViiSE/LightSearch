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
package lightsearch.server.cmd.admin;

/**
 *
 * @author ViiSE
 */
public enum AdminCommandEnum {
    LOGIN {
        @Override
        public String stringValue() {return "login";}
    },
    RESTART {
        @Override
        public String stringValue() {return "restart";}
    },
    RESTART_TIME {
        @Override
        public String stringValue() {return "restartServer";}
    },
    CLIENT_TIMEOUT {
        @Override
        public String stringValue() {return "clientTimeout";}
    },
    CLIENT_LIST {
        @Override
        public String stringValue() {return "clientList";}
    },
    KICK {
        @Override
        public String stringValue() {return "kick";}
    },
    BLACKLIST {
        @Override
        public String stringValue() {return "blacklist";}
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
        public String stringValue() {return "changeDatabase";}
    };
    
    public abstract String stringValue();
}

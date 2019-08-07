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
package lightsearch.admin.panel.cmd.admin;

/**
 *
 * @author ViiSE
 */
public enum AdminCommandEnum {
   AUTHENTICATION {
        @Override
        public String stringValue() {return "0";}
    },
    RESTART {
        @Override
        public String stringValue() {return "1";}
    },
    TIMEOUT_SERVER {
        @Override
        public String stringValue() {return "2";}
    },
    TIMEOUT_CLIENT {
        @Override
        public String stringValue() {return "3";}
    },
    CLIENT_LIST {
        @Override
        public String stringValue() {return "4";}
    },
    KICK {
        @Override
        public String stringValue() {return "5";}
    },
    BLACKLIST {
        @Override
        public String stringValue() {return "6";}
    },
    ADD_BLACKLIST {
        @Override
        public String stringValue() {return "7";}
    },
    DEL_BLACKLIST {
        @Override
        public String stringValue() {return "8";}
    },
    CREATE_ADMIN {
        @Override
        public String stringValue() {return "9";}
    },
    CHANGE_DATABASE {
        @Override
        public String stringValue() {return "10";}
    },
    EXIT {
        @Override
        public String stringValue() {return "11";}
    };
    
    public abstract String stringValue();
}

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
 *
 * @author ViiSE
 */
public enum AdminCommandContentEnum {
    COMMAND {
        @Override
        public String stringValue() {return "command";}
    },
    ADMIN_NAME {
        @Override
        public String stringValue() {return "name";}
    },
    NEW_ADMIN_NAME {
        @Override
        public String stringValue() {return "adminName";}
    },
    PASSWORD {
        @Override
        public String stringValue() {return "password";}
    },
    SERVER_TIME {
        @Override
        public String stringValue() {return "time";}
    },
    CLIENT_TIMEOUT {
        @Override
        public String stringValue() {return "time";}
    },
    IMEI {
        @Override
        public String stringValue() {return "IMEI";}
    },
    IP {
        @Override
        public String stringValue() {return "ip";}
    },
    PORT {
        @Override
        public String stringValue() {return "port";}
    },
    DATABASE_NAME {
        @Override
        public String stringValue() {return "dbName";}
    };
    
    public abstract String stringValue();
}

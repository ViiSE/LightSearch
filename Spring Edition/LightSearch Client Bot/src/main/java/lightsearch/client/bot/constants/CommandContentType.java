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
package lightsearch.client.bot.constants;

/**
 *
 * @author ViiSE
 */
public enum CommandContentType {
    
    CLIENT {
        @Override
        public String toString() { return "client"; }
    },
    SYSTEM {
        @Override
        public String toString() { return "system"; }
    },
    COMMAND {
        @Override
        public String toString() { return "command"; }
    },
    IMEI {
        @Override
        public String toString() { return "IMEI"; }
    },
    USERNAME {
        @Override
        public String toString() { return "username"; }
    },
    PASSWORD {
        @Override
        public String toString() { return "password"; }
    },
    IP {
        @Override
        public String toString() { return "ip"; }
    },
    OS {
        @Override
        public String toString() { return "os"; }
    },
    MODEL {
        @Override
        public String toString() { return "model"; }
    },
    IDENT {
        @Override
        public String toString() { return "ident"; }
    },
    BARCODE {
        @Override
        public String toString() { return "barcode"; }
    },
    SKLAD {
        @Override
        public String toString() { return "sklad"; }
    },
    TK {
        @Override
        public String toString() { return "TK"; }
    },
    CARD_CODE {
        @Override
        public String toString() { return "card_code"; }
    },
    DATA {
        @Override
        public String toString() { return "data"; }
    },
    DELIVERY {
        @Override
        public String toString() { return "delivery"; }
    },
    ID {
        @Override
        public String toString() { return "ID"; }
    },
    AMOUNT {
        @Override
        public String toString() { return "amount"; }
    }
}

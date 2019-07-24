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
public enum BotSettingsEnum {
    
    BOT_AMOUNT {
        @Override
        public String toString() { return "bot_amount"; }
    },
    BOT_LIST {
        @Override
        public String toString() { return "bot_list"; }
    },
    DELAY_BEFORE_SENDING_MESSAGE {
        @Override
        public String toString() { return "delay_before_sending_message"; }
    },
    BOT_DTO {
        @Override
        public String toString() { return "bot_dto"; }
    },
    BOT_NAME {
        @Override
        public String toString() { return "bot_name"; }
    },
    USERNAME {
        @Override
        public String toString() { return "username"; }
    },
    IMEI {
        @Override
        public String toString() { return "IMEI"; }
    },
    CARD_CODE {
        @Override
        public String toString() { return "card_code"; }
    },
    USER_IDENT {
        @Override
        public String toString() { return "ident"; }
    },
    CYCLE_AMOUNT {
        @Override
        public String toString() { return "cycle_amount"; }
    },
    CYCLE_CONTENT {
        @Override
        public String toString() { return "cycle_content"; }
    },
    TYPE {
        @Override
        public String toString() { return "type"; }
    },
    IMPLEMENTATION {
        @Override
        public String toString() { return "implementation"; }
    },
    DELIVERY {
        @Override
        public String toString() { return "delivery"; }
    },
    SEARCH_DTO {
        @Override
        public String toString() { return "search_dto"; }
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
    PRODUCT_DTO {
        @Override
        public String toString() { return "product_dto"; }
    },
    PRODUCT_LIST {
        @Override
        public String toString() { return "product_list"; }
    },
    ID {
        @Override
        public String toString() { return "id"; }
    },
    AMOUNT {
        @Override
        public String toString() { return "amount"; }
    }
}
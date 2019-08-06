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
package lightsearch.client.bot;

/**
 *
 * @author ViiSE
 */
public enum CommandType {
    
    IDENTIFIER {
        @Override
        public String toString() { return "identifier"; }
    },
    CONNECT {
        @Override
        public String toString() { return "connect"; }
    },
    SEARCH {
        @Override
        public String toString() { return "search"; }
    },
    OPEN_SOFT_CHECK {
        @Override
        public String toString() { return "open_soft_check"; }
    },
    CANCEL_SOFT_CHECK {
        @Override
        public String toString() { return "cancel_soft_check"; }
    },
    CLOSE_SOFT_CHECK {
        @Override
        public String toString() { return "close_soft_check"; }
    },
    CONFIRM_SOFT_CHECK_PRODUCTS {
        @Override
        public String toString() { return "confirm_prod_sf"; }
    },
    CLEAR_AVERAGE_TIME {
        @Override
        public String toString() { return "clear"; }
    }
}

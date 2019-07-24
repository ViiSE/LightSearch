/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    }
}
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
public enum CommandContentType {
    
    CLIENT {
        @Override
        public String toString() { return "client"; }
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

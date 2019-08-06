/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.cmd.admin;

/**
 *
 * @author ViiSE
 */
public enum AdminCommandResultEnum {
    NAME {
        @Override
        public String stringValue() { return "name"; }
    },
    IS_DONE {
        @Override
        public String stringValue() { return "isDone"; }
    },
    MESSAGE {
        @Override
        public String stringValue() { return "message"; }
    },
    DATA {
        @Override
        public String stringValue() { return "data"; }
    },
    IMEI {
        @Override
        public String stringValue() { return "IMEI"; }
    },
    USERNAME {
        @Override
        public String stringValue() { return "username"; }
    };
    
    public abstract String stringValue();
}

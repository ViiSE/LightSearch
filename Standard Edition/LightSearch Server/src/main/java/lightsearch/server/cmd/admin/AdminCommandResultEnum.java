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
 * Константы результата команд администратора LightSearch Server согласно протоколу.
 * <p>
 * Ознакомиться с протоколом можно по
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Documents/Protocol/LightSearch%20Protocol.txt">ссылке</a>.
 * @author ViiSE
 * @since 1.0
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

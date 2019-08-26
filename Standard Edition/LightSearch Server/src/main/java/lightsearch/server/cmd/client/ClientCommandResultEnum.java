/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.cmd.client;

/**
 * Константы результата команд клиента LightSearch Server согласно протоколу.
 * <p>
 * Ознакомиться с протоколом можно по
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Documents/Protocol/LightSearch%20Protocol.txt">ссылке</a>.
 * @author ViiSE
 * @since 1.0.0
 */
public enum ClientCommandResultEnum {
    IMEI {
        @Override
        public String stringValue() {return "IMEI";}
    },
    IS_DONE {
        @Override
        public String stringValue() {return "is_done";}
    },
    MESSAGE {
        @Override
        public String stringValue() {return "message";}
    };
    
    public abstract String stringValue();
}

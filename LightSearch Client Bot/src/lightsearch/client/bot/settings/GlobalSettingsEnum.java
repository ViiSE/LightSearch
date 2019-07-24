/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.settings;

/**
 *
 * @author ViiSE
 */
public enum GlobalSettingsEnum {
    
    SERVER_IP {
        @Override
        public String toString() { return "server_ip"; }
    },
    SERVER_PORT {
        @Override
        public String toString() { return "server_port"; }
    },
    DELAY_MESSAGE_DISPLAY {
        @Override
        public String toString() { return "delay_message_display"; }
    },
    BOT_SETTINGS_TYPE {
        @Override
        public String toString() { return "bot_settings_type"; }
    }
}

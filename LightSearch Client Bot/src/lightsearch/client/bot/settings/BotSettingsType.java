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
public enum BotSettingsType {
    
    SIMPLE {
        @Override
        public String toString() { return "simple"; }
    },
    ADVANCED {
        @Override
        public String toString() { return "advanced"; }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.settings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import lightsearch.client.bot.exception.MessageParserException;
import lightsearch.client.bot.parser.MessageParser;
import lightsearch.client.bot.parser.MessageParserInit;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class GlobalSettingsJSONFileImpl implements GlobalSettings {

    private final String SERVER_IP                    = GlobalSettingsEnum.SERVER_IP.toString();
    private final String SERVER_PORT                  = GlobalSettingsEnum.SERVER_PORT.toString();
    private final String DELAY_BEFORE_SENDING_MESSAGE = GlobalSettingsEnum.DELAY_BEFORE_SENDING_MESSAGE.toString();
    private final String CYCLE_AMOUNT                 = GlobalSettingsEnum.CYCLE_AMOUNT.toString();
    private final String BOT_AMOUNT                   = GlobalSettingsEnum.BOT_AMOUNT.toString();
    
    private final String serverIP;
    private final int serverPort;
    private final long delay;
    private final int cycleAmount;
    private final int botAmount;
    
    public GlobalSettingsJSONFileImpl(String fileName) {
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentDirectory currDir = CurrentDirectoryInit.currentDirectory(osDetector);
        
        try(FileInputStream fin = new FileInputStream(currDir.currentDirectory() + fileName); 
                BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            String rawMessage = br.lines().collect(Collectors.joining());
            MessageParser parser = MessageParserInit.messageParser();
            JSONObject jObj = (JSONObject) parser.parse(rawMessage);
            
            serverIP    = jObj.get(SERVER_IP).toString();
            serverPort  = Integer.parseInt(jObj.get(SERVER_PORT).toString());
            delay       = Integer.parseInt(jObj.get(DELAY_BEFORE_SENDING_MESSAGE).toString());
            cycleAmount = Integer.parseInt(jObj.get(CYCLE_AMOUNT).toString());
            botAmount   = Integer.parseInt(jObj.get(BOT_AMOUNT).toString());
        }
        catch(IOException | MessageParserException ex) {
            throw new RuntimeException("Error " + fileName + " file: " + ex.getMessage());
        }
    }

    @Override
    public long delayBeforeSendingMessage() {
        return delay;
    }

    @Override
    public int cycleAmount() {
        return cycleAmount;
    }

    @Override
    public String serverIP() {
        return serverIP;
    }

    @Override
    public int serverPort() {
        return serverPort;
    }

    @Override
    public int botAmount() {
        return botAmount;
    }
    
}

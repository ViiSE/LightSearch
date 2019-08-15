/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.message.result.type;

import lightsearch.server.cmd.client.ClientCommandResultEnum;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("messageTypeClientJSONFailDefault")
public class MessageTypeClientJSONFailDefaultImpl implements MessageType {

    private final String IMEI    = ClientCommandResultEnum.IMEI.stringValue();
    private final String IS_DONE = ClientCommandResultEnum.IS_DONE.stringValue();
    private final String MESSAGE = ClientCommandResultEnum.MESSAGE.stringValue();
    
    @Override
    public String createFormattedMessage(String IMEI, String isDone, Object message) {
        JSONObject jObj = new JSONObject();
        
        jObj.put(this.IMEI, IMEI);
        jObj.put(IS_DONE, isDone);
        jObj.put(MESSAGE, message);
        
        return jObj.toJSONString();
    }
    
}

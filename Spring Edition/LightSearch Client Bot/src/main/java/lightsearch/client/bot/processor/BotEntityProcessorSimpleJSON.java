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
package lightsearch.client.bot.processor;

import lightsearch.client.bot.*;
import lightsearch.client.bot.constants.BotSettingsEnum;
import lightsearch.client.bot.data.*;
import lightsearch.client.bot.exception.SocketException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.producer.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service("botEntityProcessorSimpleJSON")
@Scope("prototype")
public class BotEntityProcessorSimpleJSON extends BotEntityProcessor {

    private final String BOT_DAO                      = BotSettingsEnum.BOT_DAO.toString();
    private final String DELAY_BEFORE_SENDING_MESSAGE = BotSettingsEnum.DELAY_BEFORE_SENDING_MESSAGE.toString();
    private final String CYCLE_AMOUNT                 = BotSettingsEnum.CYCLE_AMOUNT.toString();
    private final String CYCLE_CONTENT                = BotSettingsEnum.CYCLE_CONTENT.toString();

    @Autowired private ConnectionDTOProducer connDTOProducer;
    @Autowired private TestCycleCreatorProducer testCycleCreatorProducer;
    @Autowired private BotSettingsDTOProducer botSettingsDTOProducer;
    @Autowired private SocketCreatorProducer socketCreatorProducer;
    @Autowired private BotDAOCreatorProducer botDAOCreatorProducer;
    @Autowired private MessageSenderProducer messageSenderProducer;
    @Autowired private MessageRecipientProducer messageRecipientProducer;
    @Autowired private BotEntityDTOProducer botEntityDTOProducer;
    @Autowired private BotEntityProducer botEntityProducer;

    @Autowired
    public BotEntityProcessorSimpleJSON(int botAmount, String ip, int port, long delayMessageDisplay) {
        super(botAmount, ip, port, delayMessageDisplay);
    }

    @Override
    public List<BotEntity> apply(Object data) {
        if(super.botAmount() <= 0)
            throw new RuntimeException("Bots amount is less or equals than 0.");
        
        JSONObject jSettings = (JSONObject) data;
        List<BotEntity> bots = new ArrayList<>();
        
        TestCycleCreator testCycleCreator = testCycleCreatorProducer.getTestCycleCreatorJSONInstance(jSettings.get(CYCLE_CONTENT));
        int cycleAmount = Integer.parseInt(jSettings.get(CYCLE_AMOUNT).toString());

        BotDAOCreator botDAOCreator = botDAOCreatorProducer.getSimpleJSONInstance(jSettings.get(BOT_DAO));
        
        ConnectionDTO connDTO = connDTOProducer.getConnectionDTODefaultInstance(super.ip(), super.port());
        
        for(int i = 0; i < super.botAmount(); i++) {
            try {
                Socket socket = socketCreatorProducer.getSocketCreatorDefaultInstance(connDTO).createSocket();
                
                MessageSender msgSender = messageSenderProducer.getMessageSenderDefaultInstance(new DataOutputStream(socket.getOutputStream()));
                MessageRecipient msgRecipient = messageRecipientProducer.getMessageRecipientDebugInstance(new DataInputStream(socket.getInputStream()));
                
                TestCycle testCycle = testCycleCreator.createCycle();
                long delayBeforeSendingMessage = Integer.parseInt(jSettings.get(DELAY_BEFORE_SENDING_MESSAGE).toString());
                BotSettingsDTO botSettings = botSettingsDTOProducer.getBotSettingsDTODefaultInstance(testCycle, cycleAmount, delayBeforeSendingMessage);
                
                BotDAO botDAO = botDAOCreator.createBotDAO();
                
                BotEntityDTO botEntityDTO = botEntityDTOProducer.getBotEntityDTODefaultInstance(botDAO, socket,
                        botSettings, msgSender, msgRecipient, super.delayMessageDisplay());
                
                bots.add(botEntityProducer.getBotEntityDefaultInstance(botEntityDTO));
            } catch (SocketException | IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        
        return bots;
    }
}

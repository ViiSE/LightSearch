/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.BotThread;
import lightsearch.client.bot.BotThreadInit;
import lightsearch.client.bot.BotEntityInit;
import lightsearch.client.bot.TestCycle;
import lightsearch.client.bot.TestCycleInit;
import lightsearch.client.bot.data.ConnectionDTO;
import lightsearch.client.bot.data.ConnectionDTOInit;
import lightsearch.client.bot.data.BotDTOInit;
import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.data.ProductDTOInit;
import lightsearch.client.bot.data.SearchDTO;
import lightsearch.client.bot.data.SearchDTOInit;
import lightsearch.client.bot.exception.SocketException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorInit;
import lightsearch.client.bot.settings.BotSettingsInit;
import lightsearch.client.bot.socket.SocketCreatorInit;
import lightsearch.client.bot.settings.BotSettings;
import lightsearch.client.bot.settings.GlobalSettings;
import lightsearch.client.bot.BotEntity;
import lightsearch.client.bot.data.BotDTO;

/**
 *
 * @author ViiSE
 */
public class BotSessionDefaultImpl implements BotSession {

    private final String serverIP;
    private final int serverPort;
    private final long delayMessageDisplay;
    private final int cycleAmount;
    private final int botAmount;

    public BotSessionDefaultImpl(GlobalSettings globalSettings) {
        this.serverIP = globalSettings.serverIP();
        this.serverPort = globalSettings.serverPort();
        this.delayBeforeSendingMessage = globalSettings.delayBeforeSendingMessage();
        this.cycleAmount = globalSettings.cycleAmount();
        this.botAmount = globalSettings.botAmount();
    }
    
    @Override
    public void createSession() {
        List<BotThread> bots = new ArrayList<>();
        
        for(int i = 0; i < botAmount; i++) {
            try {
                ConnectionDTO connectionDTO = ConnectionDTOInit.connectDTO(serverIP, serverPort);
                Socket socket = SocketCreatorInit.socketCreator(connectionDTO).createSocket();
                BotSettings settings = 
                        BotSettingsInit.lightSearchClientBotSettings(delayBeforeSendingMessage, cycleAmount);
                
                MessageSender msgSender = 
                        MessageSenderInit.messageSender(new DataOutputStream(socket.getOutputStream()));
                MessageRecipient msgRecipient = 
                        MessageRecipientInit.messageRecipient(new DataInputStream(socket.getInputStream()));
                
                BotDTO botDTO = 
                        BotDTOInit.lightSearchClientBotDTO(
                                "bot " + i, "11111111111111" + i, "00" + i, "11" + i, String.valueOf(i));
                
                SearchDTO searchDTO1 = SearchDTOInit.searchDTO("111111", "all", "all");
                SearchDTO searchDTO2 = SearchDTOInit.searchDTO("222222", "all", "null");
                SearchDTO searchDTO3 = SearchDTOInit.searchDTO("444444", "null", "all");
                
                List<ProductDTO> products = new ArrayList<>();
                products.add(ProductDTOInit.productDTO("111111", "10"));
                products.add(ProductDTOInit.productDTO("444444", "1"));
                
                List<Processor> processors = new ArrayList<>();
                processors.add(ProcessorInit.processorConnection(botDTO, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorAuthorization(botDTO, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorSearch(botDTO, searchDTO1, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorSearch(botDTO, searchDTO2, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorSearch(botDTO, searchDTO3, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorOpenSoftCheck(botDTO, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorSearch(botDTO, searchDTO1, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorSearch(botDTO, searchDTO2, msgSender, msgRecipient));
                processors.add(ProcessorInit.processorConfirmSoftCheckProducts(botDTO, msgSender, msgRecipient, products));
                processors.add(ProcessorInit.processorCloseSoftCheck(botDTO, "1", msgSender, msgRecipient));
                
                TestCycle testCycle = TestCycleInit.testCycle(processors);
                
                settings.setTestCycle(testCycle);
                
                BotEntity bot = 
                        BotEntityInit.lightSearchClientBotEntity(socket, settings);
                
                BotThread botThread = BotThreadInit.botThread(bot);
                bots.add(botThread);
            }
            catch(SocketException | IOException ignore) {}
        }
        
        bots.forEach((bot) -> { bot.start(); });
    }
}

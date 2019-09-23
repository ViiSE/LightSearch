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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotDAODefaultImpl;
import lightsearch.client.bot.data.SearchDTO;
import lightsearch.client.bot.data.SearchDTOInit;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorSearchDefaultImpl;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ProcessorSearchDefaultImplTestNG {
    
    private BotDAO botDAO;
    private MessageSender msgSender;
    private MessageRecipient msgRecipient;
    
    @BeforeClass
    @Parameters({"ip", "port", "answerSearch"})
    public void setUpClass(String ip, int port, String answerMessage) {
        Thread serverThread = new Thread(new TestProcessorServer(port, answerMessage));
        serverThread.start();
        
        botDAO = DataProviderCreator.createDataProvider(BotDAO.class);
        Socket socket = DataProviderCreator.createDataProvider(Socket.class, ip, port);
        msgSender = DataProviderCreator.createDataProvider(MessageSender.class, socket);
        msgRecipient = DataProviderCreator.createDataProvider(MessageRecipient.class, socket);
    }
    
    @Test
    @Parameters({"delayMessageDisplay"})
    public void apply(long delayMessageDisplay) {
        testBegin("ProcessorSearchDefaultImpl", "apply()");
        
        assertNotNull(botDAO, "BotDAO is null!");
        assertNotNull(msgSender, "MessageSender is null!");
        assertNotNull(msgRecipient, "MessageRecipient is null!");
        assertFalse(delayMessageDisplay < 0, "DelayMessageDisplay is less than 0!");
        
        SearchDTO searchDTO = DataProviderCreator.createDataProvider(SearchDTO.class);
        assertNotNull(searchDTO, "SearchDTO is null!");
        
        Processor proc = new ProcessorSearchDefaultImpl(searchDTO);
        proc.apply(botDAO, msgSender, msgRecipient, delayMessageDisplay);
        
        testEnd("ProcessorSearchDefaultImpl", "apply()");
    }
}

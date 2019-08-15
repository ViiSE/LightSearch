/*
 * Copyright 2019 User.
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
package lightsearch.server.message;

import lightsearch.server.exception.MessageRecipientException;
import lightsearch.server.producer.message.MessageTimeAdderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author ViiSE
 */
@Component("messageRecipientDebug")
@Scope("prototype")
public class MessageRecipientDebugImpl implements MessageRecipient {

    private final DataInputStream dataInputStream;
    private MessageTimeAdder msgTimeAdder;

    @Autowired
    MessageTimeAdderProducer producer;

    public MessageRecipientDebugImpl(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public String acceptMessage() throws MessageRecipientException {
        if(msgTimeAdder == null)
            msgTimeAdder = producer.getMessageTimeAdderDefaultInstance();

        try {
            long start = System.nanoTime();
            String res = dataInputStream.readUTF();
            long end   = System.nanoTime();
            
            System.out.println("Start: " + start);
            System.out.println("End: "   + end);
            
            long ns = end - start;
            
            System.out.println("ACCEPT: " + ns + " ns." );
            msgTimeAdder.add(ns);
            System.out.println("avg: " + msgTimeAdder.averageTime());
            
            return res;
        }
        catch(IOException ex) {
            throw new MessageRecipientException(ex.getMessage());
        }
    }
    
}

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
package lightsearch.client.bot.message;

import lightsearch.client.bot.exception.MessageRecipientException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 *
 * @author ViiSE
 */
public class MessageRecipientDebugImpl implements MessageRecipient {

    private final DataInputStream dataInputStream;
    
    public MessageRecipientDebugImpl(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public String acceptMessage() throws MessageRecipientException {
        try {
            long start = Calendar.getInstance().getTimeInMillis();
            String res = dataInputStream.readUTF();
            long end   = Calendar.getInstance().getTimeInMillis();
            
            System.out.println("Start: " + start);
            System.out.println("End: "   + end);
            
            long ms = end - start;
            
            System.out.println("ACCEPT: " + ms + " ms." );
            
            return res;
        } catch(IOException ex) {
            throw new MessageRecipientException(ex.getMessage());
        }
    }
    
}

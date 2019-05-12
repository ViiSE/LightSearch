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
package ru.viise.lightsearch.message;

import java.io.DataInputStream;
import java.io.IOException;

import ru.viise.lightsearch.exception.MessageRecipientException;

/**
 *
 * @author ViiSE
 */
public class MessageRecipientDefaultImpl implements MessageRecipient {

    private final DataInputStream dataInputStream;
    
    public MessageRecipientDefaultImpl(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public String acceptMessage() throws MessageRecipientException {
        try {
            return dataInputStream.readUTF();
        }
        catch(IOException ex) {
            throw new MessageRecipientException(ex.getMessage());
        }
    }
    
}

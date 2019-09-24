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

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Реализация интерфейса {@link lightsearch.server.message.MessageRecipient} для отладки.
 * <p>
 * Принятие сообщения происходит через {@link java.io.DataInputStream}. Читаются UTF символы.
 * <p>
 * Время принятия сообщения расчитывается при помощи вызова {@link System#nanoTime()}. Точка замера ставится перед
 * отправкой сообщения и после отправки сообщения клиенту. Затем вычисляется разность между последней и первой точками
 * замера.
 * Результат замера отображается на экране.
 * <p>
 * Используется в отладочных обработчиках команд клиента.
 * @author ViiSE
 * @see lightsearch.server.cmd.client.processor.debug
 * @since 1.0.0
 */
public class MessageRecipientDebugImpl implements MessageRecipient {

    private final DataInputStream dataInputStream;
    private final MessageTimeAdder msgTimeAdder;
    
    public MessageRecipientDebugImpl(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
        msgTimeAdder = MessageTimeAdderInit.messageTimeAdder();
    }

    @Override
    public String acceptMessage() throws MessageRecipientException {
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
        } catch(IOException ex) {
            throw new MessageRecipientException(ex.getMessage());
        }
    }
    
}

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
package lightsearch.server.message.result;

import lightsearch.server.message.result.type.MessageType;

import java.util.HashMap;
import java.util.List;

/**
 * Реализация интерфейса {@link lightsearch.server.message.result.MessageCreator} по умолчанию.
 * <p>
 * Преобразует сообщение из экземпляра класса Object в необходимый экземпляр и вызывает
 * {@link lightsearch.server.message.result.type.MessageType} для формирования необходимого сообщения.
 * @author ViiSE
 * @since 1.0.0
 */
public class MessageCreatorDefaultImpl implements MessageCreator {

    private final MessageType messageType;
    
    public MessageCreatorDefaultImpl(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String createMessage(String name, ResultTypeMessageEnum isDone, Object message) {
        if(message instanceof String) {
            String strMessage = (String)message;
            return messageType.createFormattedMessage(name, isDone.stringValue(), strMessage);
        } else if(message instanceof List) {
            List listMessage = (List)message;
            return messageType.createFormattedMessage(name, isDone.stringValue(), listMessage);
        } else if(message instanceof HashMap) {
            HashMap hashMessage = (HashMap)message;
            return messageType.createFormattedMessage(name, isDone.stringValue(), hashMessage);
        } else
            return null;
    }
    
}

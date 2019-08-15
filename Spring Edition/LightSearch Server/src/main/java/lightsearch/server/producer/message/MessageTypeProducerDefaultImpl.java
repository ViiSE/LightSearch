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

package lightsearch.server.producer.message;

import lightsearch.server.message.result.type.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("messageTypeProducerDefault")
public class MessageTypeProducerDefaultImpl implements MessageTypeProducer {

    private final String MESSAGE_TYPE_CLIENT_FAIL    = "messageTypeClientJSONFailDefault";
    private final String MESSAGE_TYPE_CLIENT_SUCCESS = "messageTypeClientJSONSuccessDefault";
    private final String MESSAGE_TYPE_ADMIN          = "messageTypeAdminJSONDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public MessageType getMessageTypeClientJSONFailDefaultInstance() {
        return ctx.getBean(MESSAGE_TYPE_CLIENT_FAIL, MessageType.class);
    }

    @Override
    public MessageType getMessageTypeClientJSONSuccessDefaultInstance() {
        return ctx.getBean(MESSAGE_TYPE_CLIENT_SUCCESS, MessageType.class);
    }

    @Override
    public MessageType getMessageTypeJSONAdminDefaultImpl() {
        return ctx.getBean(MESSAGE_TYPE_ADMIN, MessageType.class);
    }
}

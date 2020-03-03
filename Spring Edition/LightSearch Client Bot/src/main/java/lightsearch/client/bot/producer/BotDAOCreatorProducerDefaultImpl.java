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

package lightsearch.client.bot.producer;

import lightsearch.client.bot.data.BotDAOCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("botDAOCreatorProducerDefault")
public class BotDAOCreatorProducerDefaultImpl implements BotDAOCreatorProducer {

    private final String BOT_DAO_CREATOR_ADVANCE = "botDAOCreatorAdvanceJSON";
    private final String BOT_DAO_CREATOR_SIMPLE  = "botDAOCreatorSimpleJSON";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public BotDAOCreator getAdvanceJSONInstance(Object rawBotDAOInfo) {
        return (BotDAOCreator) ctx.getBean(BOT_DAO_CREATOR_ADVANCE, rawBotDAOInfo);
    }

    @Override
    public BotDAOCreator getSimpleJSONInstance(Object rawBotDAOInfo) {
        return (BotDAOCreator) ctx.getBean(BOT_DAO_CREATOR_SIMPLE, rawBotDAOInfo);
    }
}

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
package lightsearch.client.bot.parser;

import lightsearch.client.bot.exception.MessageParserException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ViiSE
 */
public class MessageParserJSONImpl implements MessageParser {

    @Override
    public Object parse(String rawMessage) throws MessageParserException {
        try {
            JSONParser parser = new JSONParser();
            JSONObject devInfo = (JSONObject)parser.parse(rawMessage);
            return devInfo;
        }
        catch (ParseException | NullPointerException | ClassCastException ex) {
            throw new MessageParserException(ex.getMessage());
        }
    }
    
}

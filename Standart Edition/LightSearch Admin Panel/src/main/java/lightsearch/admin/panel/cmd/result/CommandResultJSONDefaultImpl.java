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
package lightsearch.admin.panel.cmd.result;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class CommandResultJSONDefaultImpl implements CommandResult {
    
    private final String NAME     = CommandResultEnum.NAME.stringValue();
    private final String IS_DONE  = CommandResultEnum.IS_DONE.stringValue();
    private final String MESSAGE  = CommandResultEnum.MESSAGE.stringValue();
    private final String DATA     = CommandResultEnum.DATA.stringValue();
    
    private final JSONObject JSONMessage;

    CommandResultJSONDefaultImpl(Object parseMessage) {
        this.JSONMessage = (JSONObject)parseMessage;
    }

    @Override
    public String name() {
        return JSONMessage.get(NAME).toString();
    }

    @Override
    public String isDone() {
        return JSONMessage.get(IS_DONE).toString();
    }
    
    @Override
    public String message() {
        return JSONMessage.get(MESSAGE).toString();
    }

    @Override
    public Object data() {
        return (JSONArray)JSONMessage.get(DATA);
    }
}

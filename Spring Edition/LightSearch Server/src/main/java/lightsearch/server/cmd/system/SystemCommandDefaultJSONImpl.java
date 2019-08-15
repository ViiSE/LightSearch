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
package lightsearch.server.cmd.system;

import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("systemCommandDefaultJSON")
@Scope("prototype")
public class SystemCommandDefaultJSONImpl implements SystemCommand {

    private final String COMMAND = SystemCommandContentEnum.COMMAND.stringValue();

    private final JSONObject systemInfoObj;

    public SystemCommandDefaultJSONImpl(Object systemInfoObj) {
        this.systemInfoObj = (JSONObject)systemInfoObj;
    }

    @Override
    public String command() {
        try {
            return systemInfoObj.get(COMMAND).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }
    
}

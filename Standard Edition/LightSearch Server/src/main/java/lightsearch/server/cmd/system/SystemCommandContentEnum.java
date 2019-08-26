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

/**
 * Константы содержания команд системного бота LightSearch Server согласно протоколу.
 * <p>
 * Ознакомиться с протоколом можно по
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Documents/Protocol/LightSearch%20Protocol.txt">ссылке</a>.
 * @author ViiSE
 * @since 2.0.0
 */
public enum SystemCommandContentEnum {
    
    COMMAND {
        @Override
        public String stringValue() {return "command";}
    };
    
    public abstract String stringValue();
}

/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.cmd.result;

import lightsearch.server.data.pojo.ClientCommandResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static lightsearch.server.cmd.result.ResultType.FALSE;

@Component("clientCommandResultCreatorClientError")
@Scope("prototype")
public class ClientCommandResultCreatorErrorImpl implements ClientCommandResultCreator {

    private final String IMEI;
    private final String message;

    public ClientCommandResultCreatorErrorImpl(String IMEI, String message) {
        this.IMEI = IMEI;
        this.message = message;
    }

    @Override
    public ClientCommandResult createClientCommandResult() {
        return new ClientCommandResult(
                IMEI, FALSE.stringValue(), message, null, null, null, null);
    }
}

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
package lightsearch.server.handler.client;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.exception.ReceivedCommandVerifierException;

/**
 *
 * @author ViiSE
 */
public class ReceivedClientCommandVerifierDefaultImpl implements ReceivedClientCommandVerifier {

    @Override
    public void verifyReceivedClientCommand(ClientCommand clientCommand, String IMEI) throws ReceivedCommandVerifierException {
        if(clientCommand == null)
            throw new ReceivedCommandVerifierException("ClientCommand is null");
        if(IMEI == null)
            throw new ReceivedCommandVerifierException("Client IMEI is null");
        if(!clientCommand.IMEI().equals(IMEI))
            throw new ReceivedCommandVerifierException("Client IMEI is not verified");
    }
    
}

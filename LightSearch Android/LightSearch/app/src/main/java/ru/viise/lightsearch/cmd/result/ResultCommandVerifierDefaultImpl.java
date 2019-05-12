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

package ru.viise.lightsearch.cmd.result;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;

public class ResultCommandVerifierDefaultImpl implements ResultCommandVerifier {

    private final String TRUE = ClientCommandContentEnum.TRUE.stringValue();

    private final String incomingIMEI;
    private final String IMEI;
    private final String incomingIsDone;

    public ResultCommandVerifierDefaultImpl(String incomingIMEI, String IMEI, String incomingIsDone) {
        this.incomingIMEI = incomingIMEI;
        this.IMEI = IMEI;
        this.incomingIsDone = incomingIsDone;
    }

    @Override
    public boolean verify() {
        if(incomingIMEI.equals(IMEI))
            return incomingIsDone.equals(TRUE);
        return false;
    }
}

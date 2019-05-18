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

public class CommandResultConfirmSoftCheckProductsCreatorErrorDefaultImpl implements CommandResultCreator {

    private final boolean isDone;
    private final String message;

    public CommandResultConfirmSoftCheckProductsCreatorErrorDefaultImpl(boolean isDone, String message) {
        this.isDone = isDone;
        this.message = message;
    }

    @Override
    public CommandResult createCommandResult() {
        ConfirmSoftCheckProductsResult conSCProdRes =
                ConfirmSoftCheckProductsResultInit.confirmSoftCheckProductsResult(isDone, message, null);
        return conSCProdRes;
    }
}

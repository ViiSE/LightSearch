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
package lightsearch.server.handler.admin;

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.exception.ReceivedCommandVerifierException;

/**
 * Реализация интерфейса {@link lightsearch.server.handler.admin.ReceivedAdminCommandVerifier} по умолчанию.
 * <p>
 * Проверяет команду и имя администратора на {@code null}, и сравнивает имя администратора с именем администратора,
 * присланного в сообщении.
 * @author ViiSE
 * @since 2.0
 */
public class ReceivedAdminCommandVerifierDefaultImpl implements ReceivedAdminCommandVerifier {

    @Override
    public void verifyReceivedAdminCommand(AdminCommand admCommand, String name) throws ReceivedCommandVerifierException {
        if(admCommand == null)
            throw new ReceivedCommandVerifierException("AdminCommand is null");
        if(name == null)
            throw new ReceivedCommandVerifierException("Admin name is null");
        if(!admCommand.name().equals(name))
            throw new ReceivedCommandVerifierException("Admin name is not verified");
    }
     
}

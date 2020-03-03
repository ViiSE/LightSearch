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
package lightsearch.admin.panel.cmd.admin;

import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.util.MapRemover;

/**
 *
 * @author ViiSE
 */
public class AdminCommandCreatorInit {
    
    public static AdminCommandCreator adminCommandCreator(AdminDTO adminDTO, MapRemover mapRemover) {
        return new AdminCommandCreatorDefaultImpl(adminDTO, mapRemover);
    }

    public static AdminCommandCreator adminCommandCreatorTest(AdminDTO adminDTO, MapRemover mapRemover) {
        return new AdminCommandCreatorTestImpl(adminDTO, mapRemover);
    }
}
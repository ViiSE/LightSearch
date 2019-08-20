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

import lightsearch.admin.panel.scanner.ScannerDatabase;
import lightsearch.admin.panel.scanner.ScannerRegistration;
import lightsearch.admin.panel.scanner.ScannerRestart;
import lightsearch.admin.panel.scanner.ScannerTimeout;
import lightsearch.admin.panel.scanner.ScannerClientValue;

/**
 *
 * @author ViiSE
 */
public class ScannerCommandHolderInit {
    
    public static ScannerCommandHolder scannerCommandHolder(
            ScannerRegistration scannerRegistration,
            ScannerClientValue scannerIMEI,
            ScannerDatabase scannerDatabase,
            ScannerTimeout scannerTimeout,
            ScannerRestart scannerRestart) {
        return new ScannerCommandHolderDefaultImpl(
                scannerRegistration,
                scannerIMEI,
                scannerDatabase,
                scannerTimeout,
                scannerRestart);
    }
}

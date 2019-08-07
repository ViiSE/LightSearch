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
public class ScannerCommandHolderDefaultImpl implements ScannerCommandHolder {

    private final ScannerRegistration scannerRegistration;
    private final ScannerClientValue scannerIMEI;
    private final ScannerDatabase scannerDatabase;
    private final ScannerTimeout scannerTimeout;
    private final ScannerRestart scannerRestart;

    public ScannerCommandHolderDefaultImpl(ScannerRegistration scannerRegistration, ScannerClientValue scannerIMEI, ScannerDatabase scannerDatabase, ScannerTimeout scannerTimeout, ScannerRestart scannerRestart) {
        this.scannerRegistration = scannerRegistration;
        this.scannerIMEI = scannerIMEI;
        this.scannerDatabase = scannerDatabase;
        this.scannerTimeout = scannerTimeout;
        this.scannerRestart = scannerRestart;
    }

    @Override
    public ScannerRegistration scannerRegistration() {
        return scannerRegistration;
    }

    @Override
    public ScannerClientValue scannerClientValue() {
        return scannerIMEI;
    }

    @Override
    public ScannerDatabase scannerDatabase() {
        return scannerDatabase;
    }

    @Override
    public ScannerTimeout scannerTimeout() {
        return scannerTimeout;
    }

    @Override
    public ScannerRestart scannerRestart() {
        return scannerRestart;
    }
}

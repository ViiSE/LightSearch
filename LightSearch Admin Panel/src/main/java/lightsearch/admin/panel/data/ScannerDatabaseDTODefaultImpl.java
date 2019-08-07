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
package lightsearch.admin.panel.data;

import java.util.Scanner;
import lightsearch.admin.panel.validate.DatabaseNameValidator;
import lightsearch.admin.panel.validate.IPValidator;
import lightsearch.admin.panel.validate.PortValidator;

/**
 *
 * @author ViiSE
 */
public class ScannerDatabaseDTODefaultImpl implements ScannerDatabaseDTO {

    private final Scanner scanner;
    private final IPValidator ipValidator;
    private final PortValidator portValidator;
    private final DatabaseNameValidator dbNameValidator;

    public ScannerDatabaseDTODefaultImpl(Scanner scanner, IPValidator ipValidator,
            PortValidator portValidator, DatabaseNameValidator dbNameValidator) {
        this.scanner = scanner;
        this.ipValidator = ipValidator;
        this.portValidator = portValidator;
        this.dbNameValidator = dbNameValidator;
    }

    @Override
    public Scanner scanner() {
        return scanner;
    }

    @Override
    public IPValidator ipValidator() {
        return ipValidator;
    }

    @Override
    public PortValidator portValidator() {
        return portValidator;
    }

    @Override
    public DatabaseNameValidator dbNameValidattor() {
        return dbNameValidator;
    }
}

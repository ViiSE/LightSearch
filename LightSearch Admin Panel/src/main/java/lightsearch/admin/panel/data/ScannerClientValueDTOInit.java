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
import lightsearch.admin.panel.validate.IMEIValidator;
import lightsearch.admin.panel.validate.NumberValidator;

/**
 *
 * @author ViiSE
 */
public class ScannerClientValueDTOInit {

    public static ScannerClientValueDTO scannerIMEIDTO(Scanner scanner, 
            IMEIValidator imeiValidator, NumberValidator numberValidator) {
        return new ScannerClientValueDTODefaultImpl(scanner, imeiValidator, numberValidator);
    }
}

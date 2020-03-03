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

import lightsearch.admin.panel.security.HashAlgorithms;
import lightsearch.admin.panel.validate.AdminNameValidator;

import java.util.Scanner;

/**
 *
 * @author ViiSE
 */
public class ScannerRegistrationDTODefaultImpl implements ScannerRegistrationDTO {

    private final Scanner scanner;
    private final AdminNameValidator adminNameValidator;
    private final HashAlgorithms hashAlgorithms;

    public ScannerRegistrationDTODefaultImpl(
            Scanner scanner, AdminNameValidator adminNameValidator, HashAlgorithms hashAlgorithms) {
        this.scanner = scanner;
        this.adminNameValidator = adminNameValidator;
        this.hashAlgorithms = hashAlgorithms;
    }
    
    @Override
    public Scanner scanner() {
        return scanner;
    }

    @Override
    public AdminNameValidator adminNameValidator() {
        return adminNameValidator;
    }

    @Override
    public HashAlgorithms hashAlgorithms() {
        return hashAlgorithms;
    }
}
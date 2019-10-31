/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.validator;

import lightsearch.server.LightSearchServer;
import lightsearch.server.exception.ValidatorException;
import lightsearch.server.producer.validator.ValidatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class PortValidatorTestNG extends AbstractTestNGSpringContextTests {

    private Validator<Integer> validator;

    @Autowired
    private ValidatorProducer validatorProducer;

    @BeforeClass
    public void setUpClass() {
        validator = validatorProducer.getPortValidatorInstance();
        testBegin("PortValidator", "validate()");
    }

    @Test
    public void validate_success() {
        try {
            validator.validate(50000);
            System.out.println("Validate success");
        } catch(ValidatorException ex) {
            catchMessage(ex);
        }
    }

    @Test
    public void validate_fail_less_than_min() {
        try {
            validator.validate(505);
        } catch(ValidatorException ex) {
            catchMessage(ex);
        }
    }

    @Test
    public void validate_fail_more_than_max() {
        try {
            validator.validate(80000);
        } catch(ValidatorException ex) {
            catchMessage(ex);
        }
    }

    @AfterClass
    public void teardownClass() {
        testEnd("PortValidator", "validate()");
    }
}

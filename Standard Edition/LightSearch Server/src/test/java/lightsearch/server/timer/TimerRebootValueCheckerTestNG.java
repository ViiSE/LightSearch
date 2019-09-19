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
package lightsearch.server.timer;

import lightsearch.server.checker.TimerRebootValueChecker;
import lightsearch.server.checker.TimerRebootValueCheckerInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class TimerRebootValueCheckerTestNG {
    
    @Test
    public void check() {
        testBegin("TimerRebootValueChecker", "check()");
        
        int serverRebootValue = 24;
        assertFalse(serverRebootValue < 0, "ServerRebootValue is less than 0!");
        
        TimerRebootValueChecker timerChecker = TimerRebootValueCheckerInit.timerRebootValueChecker();
        
        System.out.println("timerChecker.check(" + serverRebootValue + "): " + timerChecker.check(serverRebootValue));
        
        testEnd("TimerRebootValueChecker", "check()");
    }
}

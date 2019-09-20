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
package lightsearch.server.checker;

import org.testng.annotations.Test;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchCheckerTestNG {
    
    private void testIsEmpty(LightSearchChecker checker, int number, String... params) {
        System.out.println("Test "  + number + ": ");
        System.out.println("Values:");
        for(String str: params) {
            if(str == null)
                System.out.println("\t \"null\" ");
            else
                System.out.println("\t \"" + str + "\"");
        }
        System.out.println("Result: " + checker.isEmpty(params));
    }
    
    private void testIsNull(LightSearchChecker checker, int number, Object... params) {
        System.out.println("Test "  + number + ": ");
        System.out.println("Values:");
        for(Object obj: params) {
            if(obj == null)
                System.out.println("\t \"null\" ");
            else
                System.out.println("\t \"" + obj.toString() + "\"");
        }
        System.out.println("Result: " + checker.isNull(params));
    }
    
    @Test
    public void isEmpty() {
        testBegin("LightSearchChecker", "isEmpty()");
        
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        
        String str1 = "str1";
        String str2 = "";
        String str3 = "str3";
        String str4 = null;
        String str5 = null;
        String str6 = "";
        
        testIsEmpty(checker, 1, str1, str3);
        testIsEmpty(checker, 2, str1, str2, str3);
        testIsEmpty(checker, 3, str1, str4, str5);
        testIsEmpty(checker, 4, str4, str5, str2);
        testIsEmpty(checker, 5, str4, str5);
        testIsEmpty(checker, 6, str2, str6);
        
        testEnd("LightSearchChecker", "isEmpty()");
    }
    
    @Test
    public void isNull() {
        testBegin("LightSearchChecker", "isNull()");
        
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        
        String str1 = "str1";
        String str2 = "";
        String str3 = "str3";
        String str4 = null;
        String str5 = null;
        String str6 = "";
        
        testIsNull(checker, 1, str1, str3);
        testIsNull(checker, 2, str1, str2, str3);
        testIsNull(checker, 3, str1, str4, str5);
        testIsNull(checker, 4, str4, str5, str2);
        testIsNull(checker, 5, str4, str5);
        testIsNull(checker, 6, str2, str6);
        
        testEnd("LightSearchChecker", "isNull()");
    }
}

/*
 *  Copyright 2019 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.updater;

import lightsearch.updater.exception.ReleaseInfoException;
import lightsearch.updater.release.info.ReleaseInfoCheckerJSONImpl;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.annotations.Test;

public class SandBox {

    @Test
    public void d() {
        String json = "{\"name\" : \"vi\", \n " +
                "\"f\": 33}";

        JSONParser parser = new JSONParser();
        try {
            parser.parse(json);
        } catch (ParseException e) {
            System.out.println("EXCEPTION: " + e.toString());
        }

        ReleaseInfoCheckerJSONImpl checker = new ReleaseInfoCheckerJSONImpl();
        try {
            checker.check(json);
        } catch (ReleaseInfoException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        BCryptPasswordEncoder f = new BCryptPasswordEncoder();
        System.out.println(f.encode("password"));
    }
}

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

package lightsearch.updater.release.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.updater.exception.ReleaseInfoException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("releaseInfoCheckerJSON")
public class ReleaseInfoCheckerJSONImpl implements ReleaseInfoChecker {

    @Override
    public void check(String infoContent) throws ReleaseInfoException {
        try {
            new ObjectMapper().readTree(infoContent);
            new JSONParser().parse(infoContent);
        } catch(IOException | ParseException ex) {
            throw new ReleaseInfoException(ex.getMessage());
        }
    }
}

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
package lightsearch.admin.panel.cmd.admin.result;

import java.util.Map;
import org.json.simple.JSONArray;

/**
 *
 * @author ViiSE
 */
public class BlacklistResultJSONDefaultImpl implements BlacklistResult {

    private final JSONArray data;
    private final Map<String, String> blacklist;

    public BlacklistResultJSONDefaultImpl(Object data, Map<String, String> blacklist) {
        this.data = (JSONArray)data;
        this.blacklist = blacklist;
    }

    @Override
    public String result() {
        String res = "";
        res += "+================================================+\n";
        int blacklistCount = 1;
        if(data != null) {
            for (int j = 0; j < data.size(); j++) {
                res += "|" + blacklistCount + ". " + data.get(j).toString();
                int spaceCount = 45 - data.get(j).toString().length();
                for(int i = 0; i < spaceCount; i++)
                    res += " ";
                res += "|\n";
                res += "+------------------------------------------------+\n";
                blacklist.put(String.valueOf(blacklistCount), data.get(j).toString());
                blacklistCount++;
            }
            blacklistCount--;
            res += "Blacklist size: " + data.size() + "\n";
        }
        else {
            res += "Blacklist size: 0\n";
        }
        return res;
    }
}

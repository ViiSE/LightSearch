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
package lightsearch.daemon.type;

import java.io.IOException;

/**
 *
 * @author ViiSE
 */
public class DaemonWindowsDefaultImpl implements Daemon {
    
    private final String lightSearchServerName;

    public DaemonWindowsDefaultImpl(String lightSearchServerName) {
        this.lightSearchServerName = lightSearchServerName;
    }

    @Override
    public void execute() {
        try {
            Runtime.getRuntime().exec(new String[] {
                "cmd.exe", "/c", 
                "cd", "C:\\Windows\\system32\\", "&", 
                "start", "cmd.exe", "/c", 
                "java", "-jar", lightSearchServerName});
        }
        catch(IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        System.exit(0);
    }
}

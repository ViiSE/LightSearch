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
package lightsearch.updater.os;

import lightsearch.updater.producer.os.CurrentDirectoryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("releasesDirectoryWindows")
public class ReleasesDirectoryWindowsImpl implements ReleasesDirectory {

    @Autowired
    private CurrentDirectoryProducer currentDirectoryProducer;

    @Override
    public String releasesDirectory() {
        CurrentDirectory currentDirectory = currentDirectoryProducer.getCurrentDirectoryDefaultInstance();
        return "file:" + File.separator + File.separator + File.separator +
                currentDirectory.currentDirectory() + "update" + File.separator + "releases" + File.separator;
    }
}

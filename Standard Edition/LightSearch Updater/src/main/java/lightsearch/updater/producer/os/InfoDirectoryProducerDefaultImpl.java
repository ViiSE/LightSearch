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
package lightsearch.updater.producer.os;

import lightsearch.updater.os.InfoDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("infoDirectoryProducerDefault")
public class InfoDirectoryProducerDefaultImpl implements InfoDirectoryProducer {

    private final String INFO_DIRECTORY_WINDOWS = "infoDirectoryWindows";
    private final String INFO_DIRECTORY_DEFAULT = "infoDirectoryDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public InfoDirectory getInfoDirectoryWindowsInstance() {
        return ctx.getBean(INFO_DIRECTORY_WINDOWS, InfoDirectory.class);
    }

    @Override
    public InfoDirectory getInfoDirectoryDefaultInstance() {
        return ctx.getBean(INFO_DIRECTORY_DEFAULT, InfoDirectory.class);
    }
}

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

package lightsearch.server.producer.log;

import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LoggerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("loggerFileProducerDefault")
public class LoggerFileProducerDefaultImpl implements LoggerFileProducer {

    private final String LOGGER_FILE = "loggerFileDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public LoggerFile getLoggerFileDefaultInstance(LogDirectory logDirectory) {
        return (LoggerFile) ctx.getBean(LOGGER_FILE, logDirectory);
    }
}

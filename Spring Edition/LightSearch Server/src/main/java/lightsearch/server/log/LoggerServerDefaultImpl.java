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
package lightsearch.server.log;

import lightsearch.server.producer.time.CurrentDateTimeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("loggerServerDefault")
@Scope("prototype")
public class LoggerServerDefaultImpl implements LoggerServer {

    @Autowired @Qualifier("logDirectoryDefault") private LogDirectory logDirectory;
    @Autowired @Qualifier("loggerFileDefault") private LoggerFile loggerFile;
    @Autowired private LoggerWindow loggerWindow;
    @Autowired private CurrentDateTimeProducer currentDateTimeProducer;

    @Override
    synchronized public void log(Class clazz, LogMessageTypeEnum type, String message) {
        loggerFile.writeLogFile(type.stringValue(), currentDateTimeProducer.getCurrentDateTimeDefaultInstance(), message);
        loggerWindow.printLog(type.stringValue(), currentDateTimeProducer.getCurrentDateTimeDefaultInstance(),
                String.format("{%s} : %s", clazz.getSimpleName(), message));
    }
    
}

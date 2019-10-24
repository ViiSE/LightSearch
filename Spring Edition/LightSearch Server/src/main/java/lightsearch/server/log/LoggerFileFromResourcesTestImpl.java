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

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.producer.checker.LightSearchCheckerProducer;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 *
 * @author ViiSE
 */
@Component("loggerFileFromResourcesTest")
public class LoggerFileFromResourcesTestImpl implements LoggerFile {

    private LightSearchChecker checker;

    @Autowired private LightSearchCheckerProducer producer;
    @Autowired @Qualifier("logDirectoryResourcesTest") private LogDirectory logDirectory;

    @Override
    public synchronized void writeLogFile(String type, CurrentDateTime currentDateTime, String message) {
        if(checker == null)
            checker = producer.getLightSearchCheckerDefaultInstance();

        if(!checker.isEmpty(type, message)) {
            try(OutputStream fout = new FileOutputStream(
                    logDirectory.logDirectory() + "log_" + currentDateTime.dateLog() + ".txt", true);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                bw.write("[" + currentDateTime.dateTimeWithDot() + "] " + type + ": " + message);
                bw.newLine();
            } catch(IOException ex) {
                System.out.println("[" + currentDateTime.dateTimeWithDot() + "] Log file Error: " + ex.getMessage());
            }
        }
    }
    
}

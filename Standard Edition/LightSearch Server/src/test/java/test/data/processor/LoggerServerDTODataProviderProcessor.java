package test.data.processor;

import lightsearch.server.log.*;

public class LoggerServerDTODataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        String logDirectory = (String) args[0];
        LogDirectory logDir = LogDirectoryInit.logDirectory(logDirectory);
        LoggerFile logFile = LoggerFileInit.loggerFile(logDir);
        LoggerWindow logWindow = LoggerWindowInit.loggerWindow();
        return LoggerServerInit.loggerServer(logFile, logWindow);
    }
}

/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.cmd.system;

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.producer.cmd.system.ProcessorSystemProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Service("systemCommandCreatorDefault")
public class SystemCommandCreatorDefaultImpl implements SystemCommandCreator {
    
    private final String CLEAR_AVERAGE_TIME = SystemCommandEnum.CLEAR_AVERAGE_TIME.stringValue();

    @Autowired
    private ProcessorSystemProducer producer;

    @Override
    public Map<String, Function<SystemCommand, CommandResult>> createCommandHolder() {
        Map<String, Function<SystemCommand, CommandResult>> result = new HashMap<>();
        result.put(CLEAR_AVERAGE_TIME, producer.getClearAverageTimeProcessorDebugInstance());
        return result;
    }
}

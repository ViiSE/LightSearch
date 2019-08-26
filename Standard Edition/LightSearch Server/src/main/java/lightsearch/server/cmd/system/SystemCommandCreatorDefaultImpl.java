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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.cmd.system.processor.ClearAverageTimeProcessorDebug;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;

/**
 * Реализация интерфейса {@link lightsearch.server.cmd.system.SystemCommandCreator} по умолчанию.
 * <p>
 * Все команды создаются согласно протоколу.
 * @author ViiSE
 * @see lightsearch.server.cmd.system.SystemCommandContentEnum
 * @since 2.0.0
 */
public class SystemCommandCreatorDefaultImpl implements SystemCommandCreator {
    
    private final String CLEAR_AVERAGE_TIME = SystemCommandEnum.CLEAR_AVERAGE_TIME.stringValue();
    
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    
    public SystemCommandCreatorDefaultImpl(LightSearchServerDTO serverDTO, 
            LightSearchListenerDTO listenerDTO) {
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
    }

    @Override
    public Map<String, Function<SystemCommand, CommandResult>> createCommandHolder() {
        Map<String, Function<SystemCommand, CommandResult>> result = new HashMap<>();
        
        result.put(CLEAR_AVERAGE_TIME, new ClearAverageTimeProcessorDebug(serverDTO, 
                listenerDTO.checker()));
        
        return result;
    }
}

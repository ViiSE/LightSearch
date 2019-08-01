/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.cmd.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.cmd.client.processor.debug.AuthenticationProcessorDebug;
import lightsearch.server.cmd.client.processor.debug.CancelSoftCheckProcessorDebug;
import lightsearch.server.cmd.client.processor.debug.ClearAverageTimeProcessorDebug;
import lightsearch.server.cmd.client.processor.debug.CloseSoftCheckProcessorDebug;
import lightsearch.server.cmd.client.processor.debug.ConfirmSoftCheckProductsProcessorDebug;
import lightsearch.server.cmd.client.processor.debug.OpenSoftCheckProcessorDebug;
import lightsearch.server.cmd.client.processor.debug.SearchProcessorDebug;
import lightsearch.server.cmd.client.processor.debug.SoftCheckDebug;
import lightsearch.server.cmd.client.processor.debug.SoftCheckDebugInit;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;

/**
 *
 * @author ViiSE
 */
public class ClientCommandCreatorDebugDefaultImpl implements ClientCommandCreator {
    private final String CONNECT                     = ClientCommandEnum.CONNECT.stringValue();
    private final String OPEN_SOFT_CHECK             = ClientCommandEnum.OPEN_SOFT_CHECK.stringValue();
    private final String CLOSE_SOFT_CHECK            = ClientCommandEnum.CLOSE_SOFT_CHECK.stringValue();
    private final String CANCEL_SOFT_CHECK           = ClientCommandEnum.CANCEL_SOFT_CHECK.stringValue();
    private final String CONFIRM_SOFT_CHECK_PRODUCTS = ClientCommandEnum.CONFIRM_SOFT_CHECK_PRODUCTS.stringValue();
    private final String SEARCH                      = ClientCommandEnum.SEARCH.stringValue();
    private final String CLEAR_AVERAGE_TIME          = ClientCommandEnum.CLEAR_AVERAGE_TIME.stringValue();
    
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final ClientDAO clientDAO;
    
    public ClientCommandCreatorDebugDefaultImpl(LightSearchServerDTO serverDTO, 
            LightSearchListenerDTO listenerDTO, ClientDAO clientDAO) {
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.clientDAO = clientDAO;
    }

    @Override
    public Map<String, Function<ClientCommand, CommandResult>> createCommandHolder() {
        Map<String, Function<ClientCommand, CommandResult>> result = new HashMap<>();
        
        SoftCheckDebug softCheckDebug = SoftCheckDebugInit.softCheckDebug();
        
        result.put(CONNECT, new AuthenticationProcessorDebug(serverDTO, listenerDTO.checker(), clientDAO));
        result.put(SEARCH,  new SearchProcessorDebug(serverDTO, listenerDTO.checker()));
        result.put(OPEN_SOFT_CHECK, new OpenSoftCheckProcessorDebug(serverDTO, listenerDTO.checker(), softCheckDebug));
        result.put(CLOSE_SOFT_CHECK, new CloseSoftCheckProcessorDebug(serverDTO, listenerDTO.checker(), softCheckDebug));
        result.put(CANCEL_SOFT_CHECK, new CancelSoftCheckProcessorDebug(serverDTO, listenerDTO.checker(), softCheckDebug));
        result.put(CONFIRM_SOFT_CHECK_PRODUCTS, new ConfirmSoftCheckProductsProcessorDebug(serverDTO, 
                listenerDTO.checker()));
        result.put(CLEAR_AVERAGE_TIME, new ClearAverageTimeProcessorDebug(serverDTO, 
                listenerDTO.checker()));
        
        return result;
    }
}

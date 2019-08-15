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

import lightsearch.server.cmd.client.processor.debug.SoftCheckDebug;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.producer.cmd.client.ProcessorClientProducer;
import lightsearch.server.producer.cmd.client.SoftCheckDebugProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Service("clientCommandCreatorDebug")
@Scope("prototype")
public class ClientCommandCreatorDebugImpl implements ClientCommandCreator {

    private final String CONNECT                     = ClientCommandEnum.CONNECT.stringValue();
    private final String OPEN_SOFT_CHECK             = ClientCommandEnum.OPEN_SOFT_CHECK.stringValue();
    private final String CLOSE_SOFT_CHECK            = ClientCommandEnum.CLOSE_SOFT_CHECK.stringValue();
    private final String CANCEL_SOFT_CHECK           = ClientCommandEnum.CANCEL_SOFT_CHECK.stringValue();
    private final String CONFIRM_SOFT_CHECK_PRODUCTS = ClientCommandEnum.CONFIRM_SOFT_CHECK_PRODUCTS.stringValue();
    private final String SEARCH                      = ClientCommandEnum.SEARCH.stringValue();
    
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final ClientDAO clientDAO;
    
    @Autowired SoftCheckDebugProducer softCheckDebugProducer;
    @Autowired ProcessorClientProducer processorClientProducer;

    public ClientCommandCreatorDebugImpl(LightSearchServerDTO serverDTO,
                                         LightSearchListenerDTO listenerDTO, ClientDAO clientDAO) {
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.clientDAO = clientDAO;
    }

    @Override
    public Map<String, Function<ClientCommand, CommandResult>> createCommandHolder() {
        Map<String, Function<ClientCommand, CommandResult>> result = new HashMap<>();
        
        SoftCheckDebug softCheckDebug = softCheckDebugProducer.getSoftCheckDebugDefaultInstance();
        
        result.put(CONNECT, processorClientProducer.getAuthenticationProcessorDebugInstance(serverDTO,
                listenerDTO.checker(), clientDAO));
        result.put(SEARCH,  processorClientProducer.getSearchProcessorDebugInstance(serverDTO, listenerDTO.checker()));
        result.put(OPEN_SOFT_CHECK, processorClientProducer.getOpenSoftCheckProcessorDebugInstance(serverDTO,
                listenerDTO.checker(), softCheckDebug));
        result.put(CLOSE_SOFT_CHECK, processorClientProducer.getCloseSoftCheckProcessorDebugInstance(serverDTO,
                listenerDTO.checker(), softCheckDebug));
        result.put(CANCEL_SOFT_CHECK, processorClientProducer.getCancelSoftCheckProcessorDebugInstance(serverDTO,
                listenerDTO.checker(), softCheckDebug));
        result.put(CONFIRM_SOFT_CHECK_PRODUCTS, processorClientProducer.getConfirmSoftCheckProductsProcessorDebugInstance(
                serverDTO, listenerDTO.checker()));
        
        return result;
    }
}

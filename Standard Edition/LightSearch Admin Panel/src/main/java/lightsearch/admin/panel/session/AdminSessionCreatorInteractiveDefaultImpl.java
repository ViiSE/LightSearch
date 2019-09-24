/*
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.admin.panel.session;

import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreatorInit;
import lightsearch.admin.panel.cmd.admin.AdminCommandEnum;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
import lightsearch.admin.panel.connect.processor.ConnectionProcessor;
import lightsearch.admin.panel.connect.processor.ConnectionProcessorInit;
import lightsearch.admin.panel.connect.processor.result.ConnectionProcessorResult;
import lightsearch.admin.panel.data.*;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreatorInit;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import lightsearch.admin.panel.exception.SocketException;
import lightsearch.admin.panel.identifier.IdentifierEnum;
import lightsearch.admin.panel.menu.AdminPanelMenu;
import lightsearch.admin.panel.menu.AdminPanelMenuCreator;
import lightsearch.admin.panel.menu.AdminPanelMenuCreatorInit;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageRecipientInit;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.scanner.ScannerChooserCommand;
import lightsearch.admin.panel.scanner.ScannerChooserCommandInit;
import lightsearch.admin.panel.socket.SocketCreator;
import lightsearch.admin.panel.socket.SocketCreatorInit;
import lightsearch.admin.panel.util.MapRemover;
import lightsearch.admin.panel.util.MapRemoverInit;

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
public class AdminSessionCreatorInteractiveDefaultImpl implements AdminPanelSessionCreator {
    
    private final String OK = IdentifierEnum.OK.stringValue();
    private final String AUTHENTICATION = AdminCommandEnum.AUTHENTICATION.stringValue();
    
    private final AdminPanelPrinter printer;
    private final ScannerConnectionDTO scannerDTO;
    private final AdminPanelDTO adminPanelDTO;
    
    public AdminSessionCreatorInteractiveDefaultImpl(AdminPanelPrinter printer,
            ScannerConnectionDTO scannerDTO, AdminPanelDTO adminPanelDTO) {
        this.printer = printer;
        this.scannerDTO = scannerDTO;
        this.adminPanelDTO = adminPanelDTO;
    }
    
    @Override
    public AdminPanelSession createSession() {
        while(true) {
            try {
                ConnectionDTOCreator connectionDTOCreator =
                        ConnectionDTOCreatorInit.connectionDTOCreator(printer, scannerDTO);
                ConnectionDTO connectionDTO = connectionDTOCreator.createConnectionDTO();

                SocketCreator socketCreator = SocketCreatorInit.socketCreator(connectionDTO);
                Socket socket = socketCreator.createSocket();
                
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                
                MessageSender messageSender = MessageSenderInit.messageSender(dataStream.dataOutputStream());
                MessageRecipient messageRecipient = MessageRecipientInit.messageRecipient(dataStream.dataInputStream());
                
                ConnectionProcessor connProc = ConnectionProcessorInit.connectionProcessor(messageSender, messageRecipient);
                ConnectionProcessorResult connProcRes = connProc.apply();
                if(connProcRes.result().equals(OK)) {
                        printer.println(OK);
                        
                        AdminDAO adminDAO = AdminDAOInit.adminDAO();
                        
                        MessageCommandDTO msgCmdDTO =
                                MessageCommandDTOInit.messageCommandDTO(messageSender, messageRecipient);
                        MessageCommandCreator msgCmdCreator =
                                MessageCommandCreatorInit.messageCommandCreator(msgCmdDTO);
                        
                        AdminDTO adminDTO = AdminDTOInit.adminDTO(
                                socket, adminDAO, printer, msgCmdCreator.createMessageCommandHolder());
                        
                        
                        MapRemover mapRemover = MapRemoverInit.mapRemover();
                        AdminCommandCreator admCmdCreator =
                                AdminCommandCreatorInit.adminCommandCreator(adminDTO, mapRemover);
                        
                        Map<String, Function<AdminPanelDTO, String>> cmdHolder = admCmdCreator.createCommandHolder();
                        
                        Function<AdminPanelDTO, String> authProcessor = cmdHolder.get(AUTHENTICATION);
                        
                        if(authProcessor != null) {
                            String result = authProcessor.apply(adminPanelDTO);
                            printer.println(result);
                            
                            ScannerChooserCommandDTOCreator scChCmdDTOCreator =
                                    ScannerChooserCommandDTOCreatorInit.scannerChooserCommandDTOCreator();
                            ScannerChooserCommandDTO scChCmdDTO =
                                    scChCmdDTOCreator.createScannerChooserCommandDTO();
                            
                            ScannerChooserCommand scChCmd = 
                                    ScannerChooserCommandInit.scannerChooserCommand(scChCmdDTO);
                            
                            AdminPanelMenuCreator menuCreator = AdminPanelMenuCreatorInit.adminMenuCreator();
                            AdminPanelMenu menu = menuCreator.createAdminMenu();
                            
                            AdminPanelSessionDTO admPanelSessionDTO = AdminPanelSessionDTOInit.adminPanelDTO(
                                    menu, adminPanelDTO, cmdHolder, printer, scChCmd);

                            return AdminPanelSessionInit.adminPanelSession(admPanelSessionDTO);
                        }                        
                } else
                    throw new RuntimeException("Cannot connect to LightSearch Server. "
                            + "Make sure that LightSearch server is running "
                            + "with these an IP address and port.");
                
            } catch (SocketException | DataStreamCreatorException ex) {
                printer.println(ex.getMessage());
            }
        }
    }
}

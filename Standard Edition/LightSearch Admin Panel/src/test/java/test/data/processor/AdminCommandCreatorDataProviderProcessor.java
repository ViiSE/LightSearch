package test.data.processor;

import lightsearch.admin.panel.cmd.admin.AdminCommandCreatorInit;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.*;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import lightsearch.admin.panel.exception.SocketException;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageRecipientInit;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import lightsearch.admin.panel.socket.SocketCreator;
import lightsearch.admin.panel.socket.SocketCreatorInit;
import lightsearch.admin.panel.util.MapRemover;
import lightsearch.admin.panel.util.MapRemoverInit;

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

import static test.message.TestMessage.catchMessage;

public class AdminCommandCreatorDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        String ip = (String) args[0];
        int port = (Integer) args[1];
        boolean openTest = (Boolean) args[2];
        AdminPanelPrinter printer;
        if(args.length == 4)
            printer = (AdminPanelPrinter) args[3];
        else
            printer = AdminPanelPrinterInit.adminPanelPrinter();
        ConnectionDTOCreator connDTOCreator = ConnectionDTOCreatorInit.connectionDTOCreatorTest(ip, port);
        ConnectionDTO connDTO = connDTOCreator.createConnectionDTO();
        SocketCreator admSocketCreator = SocketCreatorInit.socketCreator(connDTO);

        try {
            Socket adminSocket = admSocketCreator.createSocket();
            AdminDAO adminDAO = AdminDAOInit.adminDAO();

            DataStreamCreator dsCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
            dsCreator.createDataStream();
            DataStream ds = DataStreamInit.dataStream(dsCreator);

            MessageSender msgSender = MessageSenderInit.messageSender(ds.dataOutputStream());
            MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(ds.dataInputStream());

            MessageCommandDTO msgCmdDTO = MessageCommandDTOInit.messageCommandDTO(msgSender, msgRecipient);
            MessageCommandCreator msgCmdCreator = MessageCommandCreatorInit.messageCommandCreator(msgCmdDTO);

            Map<String, Function<AdminCommandDAO, CommandResult>> msgCmdHolder = msgCmdCreator.createMessageCommandHolder();

            AdminDTO adminDTO = AdminDTOInit.adminDTO(adminSocket, adminDAO, printer, msgCmdHolder);
            adminDTO.adminDAO().setName("name");

            MapRemover mapRemover = MapRemoverInit.mapRemover();

            if(openTest)
                return AdminCommandCreatorInit.adminCommandCreator(adminDTO, mapRemover);
            else
                return AdminCommandCreatorInit.adminCommandCreatorTest(adminDTO, mapRemover);
        } catch(SocketException | DataStreamCreatorException ex) {
            catchMessage(ex);
            return null;
        }
    }
}

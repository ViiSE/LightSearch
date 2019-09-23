package lightsearch.admin.panel.cmd.admin.processor;

import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import lightsearch.admin.panel.exception.MessageRecipientException;
import lightsearch.admin.panel.exception.MessageSenderException;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageRecipientInit;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static test.message.TestMessage.catchMessage;

public class TestServer implements Runnable {

    public static volatile boolean closeServer = false;

    private final int port;
    private final String answerMessage;

    private boolean isSimple = false;

    public TestServer(int port, String answerMessage) {
        this.port = port;
        this.answerMessage = answerMessage;
    }

    public void setSimpleMode(boolean isSimple) {
        this.isSimple = isSimple;
    }

    @Override
    public void run() {
        System.out.println("Test server on");
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            Socket adminSocket = serverSocket.accept();
            System.out.println("ACCEPT!");

            DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
            dataStreamCreator.createDataStream();

            DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);

            MessageSender msgSender = MessageSenderInit.messageSender(dataStream.dataOutputStream());
            MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(dataStream.dataInputStream());

            if(!isSimple) {

                String recMsgIdent = msgRecipient.acceptMessage();
                System.out.println("Received message: " + recMsgIdent);

                if (answerMessage != null) {
                    System.out.println("Send message: " + answerMessage);
                    msgSender.sendMessage(answerMessage);
                }
            }
            //noinspection StatementWithEmptyBody
            while(!closeServer) { /* Just waiting for the end of test */ }

        } catch(IOException | MessageRecipientException | DataStreamCreatorException | MessageSenderException ex) {
           catchMessage(ex);
        }

        System.out.println("Test server off");
    }
}

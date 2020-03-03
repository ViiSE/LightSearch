package test;

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

    public static volatile boolean serverOn = false;
    public static volatile boolean closeClient = false;

    private final int port;

    private static volatile String answerMessage;
    private static volatile boolean isSimple    = false;
    private static volatile boolean isRecipient = true;

    public TestServer(int port) {
        this.port = port;
    }

    public static void setSimpleMode(boolean isSimple) {
        TestServer.isSimple = isSimple;
    }

    public static void setRecipientMode(boolean isRecipient) {
        TestServer.isRecipient = isRecipient;
    }

    public static void setAnswerMessage(String answerMessage) {
        TestServer.answerMessage = answerMessage;
    }

    @Override
    public void run() {
        System.out.println("Test server on");

        serverOn = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(serverOn) {
                Socket adminSocket = serverSocket.accept();
                System.out.println("ACCEPT!");

                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
                dataStreamCreator.createDataStream();

                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);

                MessageSender msgSender = MessageSenderInit.messageSender(dataStream.dataOutputStream());
                MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(dataStream.dataInputStream());

                if (!isSimple) {
                    if (isRecipient) {
                        String recMsgIdent = msgRecipient.acceptMessage();
                        System.out.println("Received message: " + recMsgIdent);
                    }
                    if (answerMessage != null) {
                        System.out.println("Send message: " + answerMessage);
                        msgSender.sendMessage(answerMessage);
                    }
                }
                //noinspection StatementWithEmptyBody
                while (!closeClient) { /* Just waiting for the end of test */ }
                adminSocket.close();
            }
        } catch (IOException | MessageRecipientException | DataStreamCreatorException | MessageSenderException ex) {
            catchMessage(ex);
        }
        System.out.println("Test server off");
    }
}

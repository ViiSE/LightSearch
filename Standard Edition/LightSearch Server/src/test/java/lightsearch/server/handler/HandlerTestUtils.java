package lightsearch.server.handler;

import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;

import java.io.IOException;
import java.net.Socket;

public class HandlerTestUtils {

    public static boolean close = false;

    public static class Admin implements Runnable {

        private final String ip;
        private final int port;

        public Admin(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket(ip, port);
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(!close) {
                    if(i == 0) {
                        String message = "{\"identifier\":\"admin\"}";
                        dataStream.dataOutputStream().writeUTF(message);
                        String getMessage = dataStream.dataInputStream().readUTF();
                        System.out.println("ADMIN GET MESSAGE: " + getMessage);
                        i++;
                    }
                }
            } catch (IOException | DataStreamCreatorException ignore) { }
        }
    }

    public static class Client implements Runnable {

        private final String ip;
        private final int port;

        public Client(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket(ip, port);
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(!close) {
                    if(i == 0) {
                        String message = "{\"identifier\":\"client\"}";
                        dataStream.dataOutputStream().writeUTF(message);
                        String getMessage = dataStream.dataInputStream().readUTF();
                        System.out.println("CLIENT GET MESSAGE: " + getMessage);
                        i++;
                    }
                }
            } catch (IOException | DataStreamCreatorException ignore) { }
        }
    }

    public static class SystemBot implements Runnable {

        private final String ip;
        private final int port;

        public SystemBot(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket(ip, port);
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(!close) {
                    if(i == 0) {
                        String message = "{\"identifier\":\"system\"}";
                        dataStream.dataOutputStream().writeUTF(message);
                        String getMessage = dataStream.dataInputStream().readUTF();
                        System.out.println("SYSTEM BOT GET MESSAGE: " + getMessage);
                        i++;
                    }
                }
            } catch (IOException | DataStreamCreatorException ignore) { }
        }
    }
}

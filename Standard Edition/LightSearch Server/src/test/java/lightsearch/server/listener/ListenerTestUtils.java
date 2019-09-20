package lightsearch.server.listener;

import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import test.TestUtils;

import java.io.IOException;
import java.net.Socket;

public class ListenerTestUtils {

    public static class Admin implements Runnable {

        private final String ip;
        private final int port;

        public Admin(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try(Socket socket = new Socket(ip, port);) {
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(i == 0) {
                    String message = "{\"identifier\":\"admin\"}";
                    dataStream.dataOutputStream().writeUTF(message);
                    dataStream.dataOutputStream().flush();

                    String getMessage = dataStream.dataInputStream().readUTF();
                    System.out.println("ADMIN GET MESSAGE: " + getMessage);

                    String message2 = "{"
                            + "\"command\":\"connect\","
                            + "\"name\":\"admin\","
                            + "\"password\":\"ff1b44a19ac7bec57e67d034155a78c755fc012ba9d9876c8f99eadca80ce1ef\""
                            + "}";
                    dataStream.dataOutputStream().writeUTF(message2);
                    dataStream.dataOutputStream().flush();

                    System.out.println("ADMIN GET MESSAGE: " + dataStream.dataInputStream().readUTF());

                    TestUtils.sleep(4000);

                    String message3 = "{"
                            + "\"name\":\"admin\","
                            + "\"command\":\"addBlacklist\","
                            + "\"IMEI\":\"123456789123456\""
                            + "}";
                    dataStream.dataOutputStream().writeUTF(message3);
                    dataStream.dataOutputStream().flush();

                    System.out.println("ADMIN GET MESSAGE: " + dataStream.dataInputStream().readUTF());

                    TestUtils.sleep(3000);

                    String message4 = "{"
                            + "\"name\":\"admin\","
                            + "\"command\":\"restart\""
                            + "}";
                    dataStream.dataOutputStream().writeUTF(message4);
                    dataStream.dataOutputStream().flush();

                    i++;
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
            try(Socket socket = new Socket(ip, port)) {
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(i == 0) {
                    String message = "{\"identifier\":\"android\"}";
                    dataStream.dataOutputStream().writeUTF(message);
                    dataStream.dataOutputStream().flush();

                    String getMessage = dataStream.dataInputStream().readUTF();
                    System.out.println("CLIENT GET MESSAGE: " + getMessage);

                    String message2 = "{"
                            + "\"command\":\"connect\","
                            + "\"IMEI\":\"123456789123456\","
                            + "\"ip\":\"127.0.0.1\","
                            + "\"os\":\"Android 8.1 Oreo\","
                            + "\"model\":\"Nexus 5\","
                            + "\"username\":\"androidUser\","
                            + "\"password\":\"superSecretPass!12\""
                            + "}";
                    dataStream.dataOutputStream().writeUTF(message2);
                    dataStream.dataOutputStream().flush();

                    System.out.println("CLIENT GET MESSAGE: " + dataStream.dataInputStream().readUTF());

                    i++;
                }
            } catch (IOException | DataStreamCreatorException ignore) { }
        }
    }
}

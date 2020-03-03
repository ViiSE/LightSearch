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
package lightsearch.server.database;

import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import lightsearch.server.exception.DatabaseWriterException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.time.CurrentDateTimeInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DatabaseWriterTestNG {
    
    @Test
    @Parameters({"usernameDb", "passwordDb", "commandConnect", "IMEI", "usernameClient", "userIdent"})
    public void writeConnectCommand(
            String usernameDb, String passwordDb, String command, String IMEI, String usernameCl, String userIdent) {
        testBegin("DatabaseWriter", "write()");
        
        try {
            DatabaseConnection databaseConnection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, usernameDb, passwordDb);
            assertNotNull(databaseConnection, "Database connection is null!");
            
            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");
            
            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "Identifier is null!");

            String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();

            String message = DatabaseCommandMessageInit
                    .databaseCommandMessageConnection(command, IMEI, usernameCl, userIdent)
                    .message();
            DatabaseWriter writer =
                    DatabaseWriterInit.databaseWriter(databaseConnection, identifier.next(), dateTime, message);
            
            writer.write();
            
        } catch (DatabaseWriterException ex) {
            catchMessage(ex);
        }
        
        testEnd("DatabaseWriter", "write()");
    }

    @Test
    @Parameters({"usernameDb", "passwordDb", "commandSearch", "IMEI", "barcode", "sklad", "TK"})
    public void writeSearchCommand(
            String usernameDb, String passwordDb, String command, String IMEI, String barcode, String sklad, String TK) {
        testBegin("DatabaseWriter", "write()");

        try {
            DatabaseConnection databaseConnection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, usernameDb, passwordDb);
            assertNotNull(databaseConnection, "Database connection is null!");

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");

            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "Identifier is null!");

            String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();

            String message = DatabaseCommandMessageInit
                    .databaseCommandMessageSearch(command, IMEI, barcode, sklad, TK)
                    .message();
            DatabaseWriter writer =
                    DatabaseWriterInit.databaseWriter(databaseConnection, identifier.next(), dateTime, message);

            writer.write();

        } catch (DatabaseWriterException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseWriter", "write()");
    }

    @Test
    @Parameters({"usernameDb", "passwordDb", "commandOpenSC", "IMEI", "userIdent", "cardCode"})
    public void writeOpenSoftCheckCommand(
            String usernameDb, String passwordDb, String command, String IMEI, String userIdent, String cardCode) {
        testBegin("DatabaseWriter", "write()");

        try {
            DatabaseConnection databaseConnection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, usernameDb, passwordDb);
            assertNotNull(databaseConnection, "Database connection is null!");

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");

            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "Identifier is null!");

            String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();

            String message = DatabaseCommandMessageInit
                    .databaseCommandMessageOpenSoftCheck(command, IMEI, userIdent, cardCode)
                    .message();
            DatabaseWriter writer =
                    DatabaseWriterInit.databaseWriter(databaseConnection, identifier.next(), dateTime, message);

            writer.write();

        } catch (DatabaseWriterException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseWriter", "write()");
    }

    @Test
    @Parameters({"usernameDb", "passwordDb", "commandCancelSC", "IMEI", "userIdent", "cardCode"})
    public void writeCancelSoftCheckCommand(
            String usernameDb, String passwordDb, String command, String IMEI, String userIdent, String cardCode) {
        testBegin("DatabaseWriter", "write()");

        try {
            DatabaseConnection databaseConnection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, usernameDb, passwordDb);
            assertNotNull(databaseConnection, "Database connection is null!");

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");

            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "Identifier is null!");

            String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();

            String message = DatabaseCommandMessageInit.
                    databaseCommandMessageCancelSoftCheck(command, IMEI, userIdent, cardCode)
                    .message();
            DatabaseWriter writer =
                    DatabaseWriterInit.databaseWriter(databaseConnection, identifier.next(), dateTime, message);

            writer.write();

        } catch (DatabaseWriterException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseWriter", "write()");
    }

    @Test
    @Parameters({"usernameDb", "passwordDb", "commandCloseSC", "IMEI", "userIdent", "cardCode", "delivery"})
    public void writeCloseSoftCheckCommand(
            String usernameDb, String passwordDb, String command, String IMEI, String userIdent, String cardCode,
            String delivery) {
        testBegin("DatabaseWriter", "write()");

        try {
            DatabaseConnection databaseConnection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, usernameDb, passwordDb);
            assertNotNull(databaseConnection, "Database connection is null!");

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");

            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "Identifier is null!");

            String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();

            String message = DatabaseCommandMessageInit
                    .databaseCommandMessageCloseSoftCheck(command, IMEI, userIdent, cardCode, delivery)
                    .message();
            DatabaseWriter writer =
                    DatabaseWriterInit.databaseWriter(databaseConnection, identifier.next(), dateTime, message);

            writer.write();

        } catch (DatabaseWriterException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseWriter", "write()");
    }

    @Test
    @Parameters({"usernameDb", "passwordDb", "commandConfirmSCProds", "IMEI", "userIdent", "cardCode", "data"})
    public void writeConfirmSoftCheckProductsCommand(
            String usernameDb, String passwordDb, String command, String IMEI, String userIdent, String cardCode,
            String data) {
        testBegin("DatabaseWriter", "write()");

        try {
            DatabaseConnection databaseConnection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, usernameDb, passwordDb);
            assertNotNull(databaseConnection, "Database connection is null!");

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");

            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "Identifier is null!");

            String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();

            String message = DatabaseCommandMessageInit
                    .databaseCommandMessageConfirmSoftCheckProducts(command, IMEI, userIdent, cardCode, data)
                    .message();
            DatabaseWriter writer =
                    DatabaseWriterInit.databaseWriter(databaseConnection, identifier.next(), dateTime, message);

            writer.write();

        } catch (DatabaseWriterException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseWriter", "write()");
    }
}

package lightsearch.server.initialization;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.AdminsService;
import lightsearch.server.producer.initialization.AdminsCreatorProducer;
import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class AdminsCreatorTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private AdminsService<String, String> adminsService;
    @Autowired private AdminsCreatorProducer adminsCreatorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;
    @Autowired private OsDetectorProducer osDetectorProducer;

    private AdminsCreator adminsCreator;

    @BeforeClass
    public void setUp() {
        OsDetector osDetector = osDetectorProducer.getOsDetectorDefaultInstance();
        CurrentServerDirectory curDir = currentServerDirectoryProducer.getCurrentServerDirectoryFromFileInstance(osDetector);
        adminsCreator = adminsCreatorProducer.getAdminsCreatorProducerFromFileInstance(curDir, adminsService);
    }

    @Test
    public void createAdmins() {
        testBegin("AdminsCreator", "createAdmin()");

        assertNotNull(adminsCreator, "Admins is null!");
        adminsCreator.createAdmins();

        System.out.println("Admins: ");
        adminsService.admins().forEach(( name, pass) -> System.out.println("|" + name + "|" + pass + "|"));

        testEnd("AdminsCreator", "createAdmin()");
    }
}

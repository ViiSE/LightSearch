package lightsearch.server.initialization;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.producer.initialization.BlacklistCreatorProducer;
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
public class BlacklistCreatorTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private BlacklistService<String> blacklistService;
    @Autowired private BlacklistCreatorProducer blacklistCreatorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;
    @Autowired private OsDetectorProducer osDetectorProducer;

    private BlacklistCreator blacklistCreator;

    @BeforeClass
    public void setUpClass() {
        OsDetector osDetector = osDetectorProducer.getOsDetectorDefaultInstance();
        CurrentServerDirectory curDir = currentServerDirectoryProducer.getCurrentServerDirectoryFromFileInstance(osDetector);

        blacklistCreator = blacklistCreatorProducer.getBlacklistCreatorFromFileInstance(curDir, blacklistService);
    }

    @Test
    public void createBlacklist() {
        testBegin("BlacklistCreator", "createBlacklist()");

        assertNotNull(blacklistCreator, "BlacklistCreator is null!");
        blacklistCreator.createBlacklist();
        blacklistService.blacklist().forEach(IMEI -> System.out.println("|" + IMEI + "|"));

        testEnd("BlacklistCreator", "createBlacklist()");
    }

}

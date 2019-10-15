package lightsearch.server.initialization;

import lightsearch.server.LightSearchServer;
import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class CurrentServerDirectoryTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;

    private CurrentServerDirectory currentServerDirectory;

    @BeforeClass
    public void setUpClass() {
        OsDetector osDetector = osDetectorProducer.getOsDetectorDefaultInstance();
        currentServerDirectory = currentServerDirectoryProducer.getCurrentServerDirectoryFromFileInstance(osDetector);
    }

    @Test
    public void currentDirectory() {
        testBegin("CurrentServerDirectory", "currentDirectory()");

        assertNotNull(currentServerDirectory, "CurrentServerDirectory is null!");
        String curDir = currentServerDirectory.currentDirectory();
        assertNotNull(curDir, "Current directory is null!");
        assertFalse(curDir.isEmpty(), "Current directory is empty!");
        System.out.println("Current directory: " + curDir);

        testEnd("CurrentServerDirectory", "currentDirectory()");
    }
}

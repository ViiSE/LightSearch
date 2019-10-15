package lightsearch.server.initialization;

import lightsearch.server.LightSearchServer;
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
public class OsDetectorProducerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private OsDetectorProducer osDetectorProducer;

    private OsDetector osDetector;

    @BeforeClass
    public void setUpClass() {
        osDetector = osDetectorProducer.getOsDetectorDefaultInstance();
        assertNotNull(osDetector, "OsDetector is null!");
    }

    @Test
    public void isWindows() {
        testBegin("OsDetector", "isWindows()");

        System.out.println("osDetector.isWindows(): " + osDetector.isWindows());

        testEnd("OsDetector", "isWindows()");
    }

    @Test
    public void isLinux() {
        testBegin("OsDetector", "isLinux()");

        System.out.println("osDetector.isLinux(): " + osDetector.isLinux());

        testEnd("OsDetector", "isLinux()");
    }

    @Test
    public void isMacOS() {
        testBegin("OsDetector", "isMacOS()");

        System.out.println("osDetector.isMacOS(): " + osDetector.isMacOS());

        testEnd("OsDetector", "isMacOS()");
    }
}

package lightsearch.server.security;

import lightsearch.server.LightSearchServer;
import lightsearch.server.producer.security.HashAlgorithmsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class HashAlgorithmsTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private HashAlgorithmsProducer hashAlgorithmsProducer;

    private HashAlgorithms hashAlgorithms;

    @BeforeClass
    public void setUpClass() {
        hashAlgorithms = hashAlgorithmsProducer.getHashAlgorithmsDefaultInstance();
    }

    @Test
    public void sha256() {
        testBegin("HashAlgorithms", "sha256()");

        assertNotNull(hashAlgorithms, "HashAlgorithms is null!");
        String message = "superPassword2010";
        String digest = hashAlgorithms.sha256(message);
        System.out.println("Original message: " + message);
        System.out.println("Digest: " + digest);

        testEnd("HashAlgorithms", "sha256()");
    }
}

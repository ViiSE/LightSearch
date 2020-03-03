package test;

public class TestUtils {

    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch(InterruptedException ignore) {}
    }
}

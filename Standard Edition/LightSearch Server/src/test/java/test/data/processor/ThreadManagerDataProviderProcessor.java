package test.data.processor;

import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadHolderInit;
import lightsearch.server.thread.ThreadManagerInit;

import java.util.HashMap;

public class ThreadManagerDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... ignore) {
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        return ThreadManagerInit.threadManager(holder);
    }
}

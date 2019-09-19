package test.data;

import test.data.processor.DataProviderHolder;

public class DataProviderCreator {

    public static <T> T createDataProvider(Class<T> dpClass, Object... args) {
        return dpClass.cast(DataProviderHolder.holder.get(dpClass).apply(args));
    }
}

package lightsearch.server.initialization;

import java.io.File;

public class CurrentServerDirectoryFromFileDebugImpl implements CurrentServerDirectory {

    private final static String resourcesFilesPath = levelUp() + levelUp() +
            dir("src") + dir("test") + dir("resources");

    private static String levelUp() {
        return ".." + File.separator;
    }

    private static String dir(String dirName) {
        return dirName + File.separator;
    }

    private final OsDetector osDetector;

    public CurrentServerDirectoryFromFileDebugImpl(OsDetector osDetector) {
        this.osDetector = osDetector;
    }

    @Override
    public String currentDirectory() {
        return CurrentServerDirectoryInit.currentDirectory(osDetector).currentDirectory() + resourcesFilesPath;
    }
}

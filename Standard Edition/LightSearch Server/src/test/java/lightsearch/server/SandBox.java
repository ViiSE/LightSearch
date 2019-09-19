/*
 * Copyright 2019 User.
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
package lightsearch.server;

import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.thread.LightSearchThread;
import org.testng.annotations.Test;

/**
 *
 * @author User
 */
public class SandBox {

    public enum Status {
        OK("OK MAN"),
        INVALID("INVALID MAN");

        private final String message;

        Status(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @Test
    public void sandBox() {
        String example = "[{hi},{hi},{hi},";
        example = example.substring(0, example.lastIndexOf("},")) + "}";
        System.out.println(example);
        System.out.println("length: " + example.length());
        System.out.println(Status.OK.getMessage());
        System.out.println(Status.INVALID.getMessage());

        Target target = new Target();
        LightSearchThread thread = new LightSearchThread(target);
        target.setThread(thread);
        System.out.println("1. thread.isWorked: " + thread.isWorked());
        thread.start();
        thread.setIsWorked(false);
        while(!thread.isDone()) {}
        System.out.println("4. thread.isDone: " + thread.isDone());
        System.out.println("OK THREAD");

        System.out.println("Current test Dir:" + CurrentServerDirectoryInit.currentDirectoryDebug(OsDetectorInit.osDetector()).currentDirectory());
        System.out.println("Current Dir:" + CurrentServerDirectoryInit.currentDirectory(OsDetectorInit.osDetector()).currentDirectory());

        //System.out.println(example.lastIndexOf("},"));
    }

    private class Target implements Runnable {

        private LightSearchThread targetThread;

        public void setThread(LightSearchThread targetThread) {
            this.targetThread = targetThread;
        }

        @Override
        public void run() {
            System.out.println("2. thread.isWorked: " + targetThread.isWorked());
            while(targetThread.isWorked()) {}
            System.out.println("3. thread.isDone: " + targetThread.isDone());
            targetThread.setIsDone(true);
        }
    }
}

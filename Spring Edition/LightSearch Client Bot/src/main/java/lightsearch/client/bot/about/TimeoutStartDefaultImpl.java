/*
 * Copyright 2019 ViiSE.
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

package lightsearch.client.bot.about;

import org.springframework.stereotype.Component;

@Component("timeoutStartDefault")
public class TimeoutStartDefaultImpl implements TimeoutStart {

    @Override
    public void start() {
        System.out.println("Test starting...");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println("3...");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println("2...");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println("1...");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println("GO!");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
}

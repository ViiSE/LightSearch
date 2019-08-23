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
package lightsearch.server.message;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("messageTimeAdderDefault")
@Scope("prototype")
public class MessageTimeAdderDefaultImpl implements MessageTimeAdder {

    private static boolean isFirst = true;
    private static long count = 0;
    private static long sum = 0;
    
    @Override
    synchronized public void add(long time) {
        if(time >= 0) {
            if(isFirst)
                isFirst = false;
            else {
                count++;
                sum += time;
            }
        }
    }

    @Override
    public long averageTime() {
        if(count == 0) return 0;
        return (sum / count);
    }

    @Override
    synchronized public void clear() {
        count = 0;
        sum = 0;
        isFirst = true;
    }
}
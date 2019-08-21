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
package lightsearch.server.timer;

/**
 * Константы для генерации идентификаторов таймеров LightSearch Server.
 * @author ViiSE
 * @since 2.0
 */
public enum TimersIDEnum {
    REBOOT_TIMER_ID {
        @Override
        public String stringValue() {return "RebootTimer";}
    },
    ITERATOR_WRITER_TIMER_ID {
        @Override
        public String stringValue() {return "IteratorWriterTimer";}
    },
    GARBAGE_COLLECTOR_WRITER {
        @Override
        public String stringValue() {return "GarbageCollectorWriter";}
    };
    
    public abstract String stringValue();
}

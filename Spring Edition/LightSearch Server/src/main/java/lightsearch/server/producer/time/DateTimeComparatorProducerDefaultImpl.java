/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.producer.time;

import lightsearch.server.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("dateTimeComparatorProducerDefault")
public class DateTimeComparatorProducerDefaultImpl implements DateTimeComparatorProducer {

    private final String DATE_TIME_COMPARATOR = "dateTimeComparatorDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public DateTimeComparator getDateTimeComparatorDefaultInstance(String pattern) {
        return (DateTimeComparator) ctx.getBean(DATE_TIME_COMPARATOR, pattern);
    }
}

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

package lightsearch.server.producer.checker;

import lightsearch.server.checker.LightSearchChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("lightSearchCheckerProducerDefault")
public class LightSearchCheckerProducerDefaultImpl implements LightSearchCheckerProducer {

    private final String LIGHT_SEARCH_CHECKER = "lightSearchCheckerDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public LightSearchChecker getLightSearchCheckerDefaultInstance() {
        return ctx.getBean(LIGHT_SEARCH_CHECKER, LightSearchChecker.class);
    }
}

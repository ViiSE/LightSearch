/*
 *  Copyright 2019 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.updater.util;

import lightsearch.updater.LightSearchUpdater;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lightsearch.updater.message.TestMessage.testBegin;
import static lightsearch.updater.message.TestMessage.testEnd;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = LightSearchUpdater.class)
public class ComparatorStringToNumberTestNG extends AbstractTestNGSpringContextTests {

    private List<String> list;

    @BeforeClass
    public void setUpClass() {
        list = new ArrayList<>();
        list.add("item1");
        list.add("item10");
        list.add("item11");
        list.add("item12");
        list.add("item2");
        list.add("item3");
        list.add("item4");
        list.add("item5");
    }

    @Test
    public void comparatorStringToNumber() {
        testBegin("ComparatorStringToNumber", "comparatorStringToNumber()");

        assertNotNull(list, "List is null!");
        System.out.println("List before: \n" + list.toString());
        List<String> sortedList = list.stream().sorted(new ComparatorStringToNumber()).collect(Collectors.toList());
        System.out.println("List after: \n" + sortedList.toString());

        testEnd("ComparatorStringToNumber", "comparatorStringToNumber()");
    }
}

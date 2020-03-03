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

package lightsearch.updater;import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaticResourcesJUnitTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUpClass() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }


    @Test
    public void testGetUpdateInfo() throws Exception {
        mockMvc.perform(get("/update/info/update.json").contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetVersionReleaseOK() throws Exception {
        mockMvc.perform(get("/update/releases/v1.0/apk-release.apk").contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetVersionReleaseNotFound() throws Exception {
        mockMvc.perform(get("/update/releases/v1.3/apk-release.apk").contentType(MediaType.ALL))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetReleaseNotFound() throws Exception {
        mockMvc.perform(get("/update/releases/v1.1/apk-release.apk").contentType(MediaType.ALL))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetNotStaticResource() throws Exception {
        mockMvc.perform(get("/update/not-static/info/update.json").contentType(MediaType.ALL))
                .andExpect(status().isFound());
    }
}

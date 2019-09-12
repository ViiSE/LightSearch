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

package lightsearch.updater.apk;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component("apkDefault")
@Scope("prototype")
public class APKDefault implements APK {

    private String name;
    private String version;
    private byte[] content;

    @Override
    public void setAPKName(String name) {
        this.name = name;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setContent(Object stream) throws IOException {
        if(stream instanceof InputStream)
            this.content = IOUtils.toByteArray((InputStream)stream);
    }

    @Override
    public String getAPKName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}

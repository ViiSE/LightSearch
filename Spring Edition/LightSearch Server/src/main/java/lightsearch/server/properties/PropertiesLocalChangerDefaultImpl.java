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

package lightsearch.server.properties;

import lightsearch.server.data.pojo.Property;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.exception.ReaderException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("propertiesLocalChangerDefault")
@Scope("prototype")
public class PropertiesLocalChangerDefaultImpl implements PropertiesLocalChanger<List<String>> {

    private final Map<String, Property> props;
    private final PropertiesReader<List<String>> propertiesReader;

    public PropertiesLocalChangerDefaultImpl(Map<String, Property> props, PropertiesReader<List<String>> propertiesReader) {
        this.props = props;
        this.propertiesReader = propertiesReader;
    }

    @Override
    public List<String> getChangedProperties() throws PropertiesException {
        try {
            return propertiesReader.read().stream().map(property -> {
                final StringBuilder result = new StringBuilder();
                props.forEach((key, prop) -> {
                    if(property.contains(key)) {
                        Object[] writeValues = new Object[prop.getValues().length + 1];
                        writeValues[0] = key;
                        System.arraycopy(prop.getValues(), 0, writeValues, 1, prop.getValues().length);
                        result.append(String.format(prop.getFormat(), writeValues));
                    }
                });
                if(result.toString().isEmpty())
                    return property;
                else
                    return result.toString();
            }).collect(Collectors.toList());
        } catch (ReaderException ex) {
            throw new PropertiesException(ex.getMessage(), ex.getLogMessage());
        }
    }
}

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
package lightsearch.admin.panel.util;

import java.util.Map;

/**
 *
 * @author ViiSE
 */
public class MapRemoverDefaultImpl implements MapRemover {
    
    @Override
    public boolean removeFromMap(Map map, Object value) {
        if(map.containsKey(value))
            return removeFromMapWithKey(map, value);
        else if(map.containsValue(value)) {
            String foundKey = findKey(map, value);
            
            if(foundKey == null)
                return false;
            
            return removeFromMapWithKey(map, foundKey);
        } else
            return false;
    }
    
    @SuppressWarnings("unchecked")
    private boolean removeFromMapWithKey(Map map, Object key) {
        int begin;
        if(key instanceof String)
            begin = Integer.parseInt((String)key);
        else if(key instanceof Integer)
            begin = (Integer)key;
        else
            return false;
        int end = map.size();
        
        for(int i = begin; i < end; i++) {
            String oldKey = String.valueOf(i);
            String oldValue = map.get(oldKey).toString();

            String newKey = String.valueOf(i + 1);
            String newValue = map.get(newKey).toString();

            map.replace(oldKey, oldValue, newValue);
        }
        
        String lastKey = String.valueOf(end);
        map.remove(lastKey);
        
        return true;
    }
    
    private String findKey(Map map, Object value) {
        for(Object key: map.keySet())
            if(map.get(key).equals(value))
                return key.toString();
        return null;
    }
}

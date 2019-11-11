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

package lightsearch.server.data.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private final String subdiv;
    private String id;
    private final String name;
    private final String amount;
    private final String price;
    private final String ei;

    @JsonCreator
    public Product(
            @JsonProperty("subdiv") String subdiv,
            @JsonProperty("id")     String id,
            @JsonProperty("name")   String name,
            @JsonProperty("amount") String amount,
            @JsonProperty("price")  String price,
            @JsonProperty("ei")     String ei) {
        this.subdiv = subdiv;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.ei = ei;
    }

    public String getSubdiv() {
        return subdiv;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getPrice() {
        return price;
    }

    public String getEi() {
        return ei;
    }
}

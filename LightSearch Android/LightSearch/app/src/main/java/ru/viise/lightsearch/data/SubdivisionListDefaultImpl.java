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

package ru.viise.lightsearch.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubdivisionListDefaultImpl implements SubdivisionList {

    private final List<Subdivision> subdivisions;
    private final String unitAmount;

    public SubdivisionListDefaultImpl(String unitAmount) {
        this.subdivisions = new ArrayList<>();
        this.unitAmount = unitAmount;
    }

    @Override
    public void addSubdivision(Subdivision subdivision) {
        subdivisions.add(subdivision);
    }

    @Override
    public Collection<Subdivision> collection() {
        return subdivisions;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Subdivision subdivision : subdivisions) {
            res.append("\t")
                    .append(subdivision.name())
                    .append(" - ")
                    .append(subdivision.productAmount())
                    .append(" ")
                    .append(unitAmount)
                    .append("\n");
        }
        return res.toString();
    }
}

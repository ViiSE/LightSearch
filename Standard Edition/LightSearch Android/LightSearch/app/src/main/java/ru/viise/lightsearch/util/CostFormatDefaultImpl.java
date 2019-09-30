/*
 * Copyright 2016 javiersantos.
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

package ru.viise.lightsearch.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CostFormatDefaultImpl implements CostFormat {

    @Override
    public float format(float totalCost) {
        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(currentLocale);
        formatSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.##", formatSymbols);

        return Float.parseFloat(decimalFormat.format(totalCost));
    }
}

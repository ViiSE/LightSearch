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

package ru.viise.lightsearch.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.data.SearchRecord;
import ru.viise.lightsearch.data.UnitsEnum;
import ru.viise.lightsearch.fragment.util.ViewFillerInit;
import ru.viise.lightsearch.fragment.util.ViewFillerProxy;
import ru.viise.lightsearch.fragment.util.ViewFillerProxyInit;

public class ResultSearchArrayAdapter extends ArrayAdapter<SearchRecord> {

    public ResultSearchArrayAdapter(@NonNull Context context, int resource, @NonNull List<SearchRecord> records) {
        super(context, resource, records);
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        SearchRecord record = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cardview_row_result_search, null);
        }

        if (record != null) {
            ((TextView) convertView.findViewById(R.id.textViewCardNameRS)).setText(record.name());
            ((TextView) convertView.findViewById(R.id.textViewCardIDRS)).setText(record.barcode());
            ((TextView) convertView.findViewById(R.id.textViewCardAmountRS)).setText(
                    String.format("%s", record.maxAmountWithUnit()));

            if(record.subdivisions().collection().size() > 1) {
                ViewFillerProxy viewFillerProxy = ViewFillerProxyInit.viewFillerProxy();
                viewFillerProxy.setViewFiller(ViewFillerInit.viewFiller(convertView));
                record.subdivisions().collection().forEach(subdivision -> viewFillerProxy.addView(subdivision, record.amountUnit()));
            }
            else if(record.subdivisions().collection().size() == 1) {
                ((TextView) convertView.findViewById(R.id.textViewCardSubdivRS)).setText(record.getSubdivision(0).name());
                ((TextView) convertView.findViewById(R.id.textViewCardSubdivAmount)).setText(
                        String.format("%s %s", record.getSubdivision(0).productAmount(), record.amountUnit()));
            }

            ((TextView) convertView.findViewById(R.id.textViewCardPriceRS)).setText(
                    String.format("%s %s", record.price(), UnitsEnum.CURRENT_PRICE_UNIT.stringValue()));
        }

        return convertView;
    }
}

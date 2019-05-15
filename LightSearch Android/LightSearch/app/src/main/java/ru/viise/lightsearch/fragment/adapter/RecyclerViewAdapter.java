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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.data.CartRecord;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DefaultViewHolder> {

    private final TextView twTotalCost;
    private final String total;
    private List<CartRecord> data;
    private final String priceUnit;

    public RecyclerViewAdapter(List<CartRecord> data, TextView twTotalCost, String priceUnit) {
        this.data = data;
        this.twTotalCost = twTotalCost;
        total = twTotalCost.getText().toString() + " ";
        this.priceUnit = priceUnit;
        getTotalCost();
    }

    private void getTotalCost() {
        float totalCost = 0;
        for(CartRecord record : data) {
            totalCost += record.totalCost();
        }
        String res = total + totalCost + " " + priceUnit;
        twTotalCost.setText(res);
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder {

        private TextView twCardName;
        private TextView twCardBarcode;
        private TextView twCardMaxAmount;
        private TextView twCardPrice;
        private TextView twCardTotalCost;
        private EditText etCardCurrentAmount;

        private boolean ignore = false;

        DefaultViewHolder(View itemView) {
            super(itemView);
            twCardName          = itemView.findViewById(R.id.textViewCardNameSC);
            twCardBarcode       = itemView.findViewById(R.id.textViewCardBarcodeSC);
            twCardMaxAmount     = itemView.findViewById(R.id.textViewCardMaxAmountSC);
            twCardPrice         = itemView.findViewById(R.id.textViewCardPriceSC);
            twCardTotalCost     = itemView.findViewById(R.id.textViewCardTotalCostSC);
            etCardCurrentAmount = itemView.findViewById(R.id.editTextCardCurrentAmountSC);

            etCardCurrentAmount.addTextChangedListener(new TextWatcher() {

                String prevString = "";
                boolean delAction = false;
                int caretPos = 0;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    caretPos = etCardCurrentAmount.getSelectionStart();
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.length() < prevString.length())
                        delAction = true;
                    else
                        delAction = false;
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!ignore) {
                        if (etCardCurrentAmount.getText().toString().isEmpty()) {
                            int pos = getAdapterPosition();
                            data.get(pos).setProductsCount(0);
                            twCardTotalCost.setText(data.get(pos).totalCostWithUnit());
                        } else {
                            int pos = getAdapterPosition();
                            float curAmount = Float.parseFloat(etCardCurrentAmount.getText().toString());
                            data.get(pos).setProductsCount(curAmount);
                            twCardTotalCost.setText(data.get(pos).totalCostWithUnit());
                            ignore = true;
                            if (!delAction) {
                                prevString = editable.toString().toLowerCase();
                                etCardCurrentAmount.removeTextChangedListener(this);
                                etCardCurrentAmount.setText(String.valueOf(data.get(pos).currentAmount()));
                                etCardCurrentAmount.addTextChangedListener(this);
                                etCardCurrentAmount.setSelection(caretPos + 1);
                            } else {
                                etCardCurrentAmount.setSelection(caretPos - 1);
                                prevString = editable.toString();
                            }
                            getTotalCost();
                            ignore = false;
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row_open_soft_check, parent, false);
        return new DefaultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        holder.twCardName.setText(data.get(position).name());
        holder.twCardBarcode.setText(data.get(position).barcode());
        holder.twCardMaxAmount.setText(data.get(position).maxAmountWithUnit());
        holder.twCardPrice.setText(data.get(position).priceWithUnit());
        holder.ignore = true;
        holder.etCardCurrentAmount.setText(String.valueOf(data.get(position).currentAmount()));
        holder.ignore = false;
        holder.twCardTotalCost.setText(data.get(position).priceWithUnit());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(int position) {
        data.remove(position);
        getTotalCost();
        notifyItemRemoved(position);
    }

    public void restoreItem(CartRecord record, int position) {
        data.add(position, record);
        getTotalCost();
        notifyItemInserted(position);
    }

    public void addItem(CartRecord record) {
        data.add(record);
        getTotalCost();
        notifyDataSetChanged();
    }

    public CartRecord getItem(int position) {
        return data.get(position);
    }

    public List<CartRecord> getData() {
        return data;
    }
}

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

import android.os.Parcel;
import android.os.Parcelable;

public class SoftCheckRecordDefaultImpl implements SoftCheckRecord {

    private final String name;
    private final String barcode;
    private final float maxAmount;
    private final float price;
    private final String unitAmount;
    private final String unitPrice = "руб.";
    private final SubdivisionList subdivisions;

    private float currentAmount;
    private float totalCost;

    public SoftCheckRecordDefaultImpl(String name, String barcode, String price, String unitAmount,
                                      SubdivisionList subdivisions) {
        this.name = name;
        this.barcode = barcode;
        this.price = Float.parseFloat(price);
        this.unitAmount = unitAmount;
        this.subdivisions = subdivisions;

        int tempMaxAmount = 0;
        for(Subdivision subdivision : subdivisions.collection())
            tempMaxAmount += subdivision.productAmount();

        maxAmount = tempMaxAmount;

        currentAmount = 1;
        totalCost = this.price;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String barcode() {
        return barcode;
    }

    @Override
    public void setProductsCount(float count) {
        currentAmount = count;
        if(currentAmount > maxAmount)
            currentAmount = maxAmount;
        if(currentAmount < 0)
            currentAmount = 0;
        totalCost = price * currentAmount;
    }

    @Override
    public String totalCostWithUnit() {
        return totalCost + " " + unitPrice;
    }

    @Override
    public float totalCost() {
        return totalCost;
    }

    @Override
    public String priceWithUnit() {
        return price + " " + unitPrice;
    }

    @Override
    public float currentAmount() {
        return currentAmount;
    }

    @Override
    public String maxAmountWithUnit() {
        return maxAmount + " " + unitAmount;
    }

    @Override
    public SubdivisionList subdivisions() {
        return subdivisions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(barcode);
        parcel.writeFloat(price);
        parcel.writeFloat(maxAmount);
        parcel.writeString(unitAmount);
        parcel.writeParcelable(subdivisions, i);
    }

    private SoftCheckRecordDefaultImpl(Parcel in) {
        name = in.readString();
        barcode = in.readString();
        price = in.readFloat();
        maxAmount = in.readFloat();
        unitAmount = in.readString();
        subdivisions = in.readParcelable(SubdivisionListDefaultImpl.class.getClassLoader());
    }

    public static final Parcelable.Creator<SoftCheckRecord> CREATOR
            = new Parcelable.Creator<SoftCheckRecord>() {

        @Override
        public SoftCheckRecord createFromParcel(Parcel in) {
            return new SoftCheckRecordDefaultImpl(in);
        }

        @Override
        public SoftCheckRecord[] newArray(int size) {
            return new SoftCheckRecordDefaultImpl[size];
        }
    };
}

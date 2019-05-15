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

public class CartRecordDefaultImpl implements CartRecord {

    private final String name;
    private final String barcode;
    private final float maxAmount;
    private final float price;
    private final String unitAmount;
    private final String unitPrice = "руб.";

    private float currentAmount;
    private float totalCost;

    public CartRecordDefaultImpl(String name, String barcode, float price, float maxAmount, String unitAmount) {
        this.name = name;
        this.barcode = barcode;
        this.maxAmount = maxAmount;
        this.price = price;
        this.unitAmount = unitAmount;
        currentAmount = 1;
        totalCost = price;
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
}

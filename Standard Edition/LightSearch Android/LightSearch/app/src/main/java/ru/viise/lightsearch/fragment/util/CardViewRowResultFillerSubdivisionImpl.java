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

package ru.viise.lightsearch.fragment.util;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.viise.lightsearch.data.Subdivision;

public class CardViewRowResultFillerSubdivisionImpl implements ViewFiller {

    private final float scale;
    private final CardView cardView;

    private int cardHeightPx = 0;

    public CardViewRowResultFillerSubdivisionImpl(View cardView) {
        this.scale    = cardView.getContext().getResources().getDisplayMetrics().density;
        this.cardView = (CardView) cardView;
    }

    @Override
    public void addView(Object... elements) {
        Subdivision subdivision = (Subdivision) elements[0];
        cardHeightPx += increase();

        cardView.setLayoutParams(new CardView.LayoutParams(cardView.getWidth(), cardHeightPx));

        int tvSubdivRSWidthPx  = (int) (250 * scale + 0.5f);
        int tvSubdivRSHeightPx = (int) (20  * scale + 0.5f);
        RelativeLayout.LayoutParams tvSubdivRSLayout = new RelativeLayout.LayoutParams(tvSubdivRSWidthPx, tvSubdivRSHeightPx);
        tvSubdivRSLayout.setMarginStart((int) (5 * scale + 0.5f));
        tvSubdivRSLayout.setMargins(0, (int) (65 * scale + 0.5f), 0, 0);
        TextView tvSubdivRS = new TextView(cardView.getContext());
        tvSubdivRS.setLayoutParams(tvSubdivRSLayout);
        tvSubdivRS.setTextSize(15);

        int tvSubdivRSAmountWidthPx  = (int) (112 * scale + 0.5f);
        int tvSubdivRSAmountHeightPx = (int) (20  * scale + 0.5f);
        RelativeLayout.LayoutParams tvSubdivRSAmountLayout = new RelativeLayout.LayoutParams(tvSubdivRSAmountWidthPx, tvSubdivRSAmountHeightPx);
        tvSubdivRSAmountLayout.setMarginStart((int) (5 * scale + 0.5f));
        tvSubdivRSAmountLayout.setMargins(0, (int) (65 * scale + 0.5f), 0, 0);
        tvSubdivRSAmountLayout.addRule(RelativeLayout.END_OF, tvSubdivRS.getId());
        TextView tvSubdivAmountRS = new TextView(cardView.getContext());
        tvSubdivAmountRS.setLayoutParams(tvSubdivRSLayout);
        tvSubdivAmountRS.setTextSize(15);

        tvSubdivRS.setText(subdivision.name());
        tvSubdivAmountRS.setText(String.valueOf(subdivision.productAmount()));

        cardView.addView(tvSubdivRS);
        cardView.addView(tvSubdivAmountRS);
    }

    private int increase() {
        return (int) (cardView.getHeight() + (20 * scale + 0.5f));
    }
}

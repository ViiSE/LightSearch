/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.cmd.client.processor.debug;

/**
 * Реализация интерфейса {@link lightsearch.server.cmd.client.processor.debug.ProductDebug} по умолчанию.
 * @author ViiSE
 * @since 2.0.0
 */
public class ProductDebugDefaultImpl implements ProductDebug {

    private final String subdivision;
    private final String id;
    private final String name;
    private final String price;
    private String amount;
    private final String unit;

    public ProductDebugDefaultImpl(String subdivision, String id, String name, String price, String amount, String unit) {
        this.subdivision = subdivision;
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.unit = unit;
    }
    
    @Override
    public String subdivision() {
        return subdivision;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String price() {
        return price;
    }

    @Override
    public String amount() {
        return amount;
    }

    @Override
    public String unit() {
        return unit;
    }

    @Override
    public void delMaxAmount(float value) {
        float amountFloat = Float.parseFloat(amount);
        float newAmount = amountFloat - value;
        amount = String.valueOf(newAmount);
    }
}

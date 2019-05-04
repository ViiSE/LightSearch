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
 *
 * @author ViiSE
 */
public class ProductDebugDefaultImpl implements ProductDebug {

    private final String podrazdelenie;
    private final String id;
    private final String name;
    private final String price;
    private final String amount;

    public ProductDebugDefaultImpl(String podrazdelenie, String id, 
            String name, String price, String amount) {
        this.podrazdelenie = podrazdelenie;
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    
    @Override
    public String podrazdelenie() {
        return podrazdelenie;
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
}

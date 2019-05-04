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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ViiSE
 */
public class SoftCheckDebugDefaultImpl implements SoftCheckDebug {

    private static boolean isOpen  = false;
    private static boolean isClose = false;
    private final List<ProductDebug> products;
    
    public SoftCheckDebugDefaultImpl() {
        products = new ArrayList<>();
    }

    @Override
    public List<ProductDebug> products() {
        return products;
    }

    @Override
    public void addProduct(ProductDebug product) {
        products.add(product);
    }

    @Override
    public void delProduct(String id) {
        for(ProductDebug product: products) {
            if(product.id().equals(id)) {
                products.remove(product);
                return;
            }
        }
    }

    @Override
    public void delAllProducts() {
        products.clear();
    }

    @Override
    public boolean closeSoftCheck() {
        if(isOpen) {
            if(!isClose) {
                isOpen  = false;
                isClose = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean openSoftCheck() {
        if(!isOpen) {
            isOpen = true;
            isClose = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelSoftCheck() {
        if(isOpen) {
            isOpen = false;
            isClose = false;
            return true;
        }
        return false;
    }
}

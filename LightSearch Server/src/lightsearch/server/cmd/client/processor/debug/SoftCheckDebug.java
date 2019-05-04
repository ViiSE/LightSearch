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

import java.util.List;

/**
 *
 * @author ViiSE
 */
public interface SoftCheckDebug {
    boolean closeSoftCheck();
    boolean openSoftCheck();
    boolean cancelSoftCheck();
    List<ProductDebug> products();
    void addProduct(ProductDebug product);
    void delProduct(String id);
    void delAllProducts();
}

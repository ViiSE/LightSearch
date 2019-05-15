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
public interface ProductDebug {
    String subdivision();
    String id();
    String name();
    String price();
    String amount();
    String unit();
}

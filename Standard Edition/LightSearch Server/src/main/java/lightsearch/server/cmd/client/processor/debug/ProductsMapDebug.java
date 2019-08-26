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

import java.util.HashMap;
import java.util.Map;

/**
 * Контейнер для отладочных товаров предприятия.
 * <p>
 * Интерфейс со статической картой-контейнером, содержащая отладочные товары.
 * @author ViiSE
 * @see lightsearch.server.cmd.client.processor.debug.ProductDebug
 * @since 2.0.0
 */
public interface ProductsMapDebug {
    
    Map<String, ProductDebug> PRODUCTS = new HashMap<String, ProductDebug>() {{
        put("1", ProductDebugInit.productDebug("Склад 1", "111111", "Товар 1", "100.0", "5", "шт."));
        put("2", ProductDebugInit.productDebug("Склад 1", "222222", "Товар 2", "1500.0", "10", "шт."));
        put("3", ProductDebugInit.productDebug("Склад 2", "111111", "Товар 1", "100.0", "100", "шт."));
        put("4", ProductDebugInit.productDebug("Склад 2", "444444", "Товар 4", "65.0", "7", "шт."));
        put("5", ProductDebugInit.productDebug("Склад 2", "2200000738592", "Товар 5", "70.0", "10", "шт."));
        put("6", ProductDebugInit.productDebug("ТК 1", "111111", "Товар 1", "100.0", "7", "шт."));
        put("7", ProductDebugInit.productDebug("ТК 2", "444444", "Товар 4", "65.0", "12", "шт."));
        put("8", ProductDebugInit.productDebug("ТК 2", "2200000738592", "Товар 5", "70.0", "20", "шт."));
    }};
}

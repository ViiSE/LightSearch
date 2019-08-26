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
 * Товар, который добавляется в мягкий чек.
 * <p>
 * Отладочный интерфейс товара предприятия. Принадлежит к какому-нибудь подразделению и имеет свой штрих-код.
 * Используется при подтверждении товаров мягкого чека и поиска товара
 * @author ViiSE
 * @since 2.0
 */
public interface ProductDebug {
    String subdivision();
    String id();
    String name();
    String price();
    String amount();
    String unit();
    /**
     * Удаляет максимальное количество данного товара в подразделении. Необходимо для симуляции случая, когда данного
     * товара в мягком чеке больше, чем свободных остатков на складах (максимального количества).
     * @param value Значение, на которое нужно уменьшить максимальное количество товара.
     */
    void delMaxAmount(float value);
}

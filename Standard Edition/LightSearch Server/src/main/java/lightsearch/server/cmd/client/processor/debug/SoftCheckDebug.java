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
 * Мягкий чек предприятия для отладки.
 * <p>
 * Мягкий чек можно закрывать, открывать и отменять.
 * <p>
 * Нельзя открыть мягкий чек, если он до этого уже был открыт.
 * <p>
 * Нельзя отменить или закрыть мягкий чек, если он еще не был открыт.
 * @author ViiSE
 * @since 2.0
 */
public interface SoftCheckDebug {
    boolean closeSoftCheck();
    boolean openSoftCheck();
    boolean cancelSoftCheck();
}

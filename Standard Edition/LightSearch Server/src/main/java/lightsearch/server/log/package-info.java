/**
 * Содержит интерфейсы и их реализации для логирования LightSearch Server.
 * <p>
 * Сообщение лога имеет структуру {@code [dd.mm.yyyy HH:MM:SS] Info/Error: Your message here.}
 * <p>
 * Если сообщение несет информативный характер, то это сообщение типа Info. Пример сообщения типа Info:
 * <p>
 * {@code [20.08.2019 09:45:10] Info: Server started.}
 * <p>
 * Если сообщение несет ошибку, которая возникла в результате работы LightSearch Server, то это сообщение типа Error.
 * Пример сообщения типа Error:
 * <p>
 * {@code [20.08.2019 09:45:10] Error:
 * C:\LightSearch\Standard Edition\LightSearch Server\logs\log_20_08_2019.txt (Системе не удается найти указанный путь).}
 * <p>
 *
 * Логирования используется повсеместно во всем проекте.
 * @author ViiSE
 * @version 1.0
 * @since 1.0.0
 */
package lightsearch.server.log;
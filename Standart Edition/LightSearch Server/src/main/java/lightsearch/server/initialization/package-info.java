/**
 * Этот пакет предоставляет интерфейсы и их реализации, необходимые для инициализации сервера. При помощи этих
 * интерфейсов считываются настройки для старта сервера, запускаются таймеры и лог. Эти интерфейсы необходимы для
 * создания экземпляров классов, реализующих интерфейсы {@link lightsearch.server.data.LightSearchServerDTO},
 * {@link lightsearch.server.data.LightSearchListenerDTO}, {@link lightsearch.server.log.LoggerServer}, которые
 * используются в реализации интерфейса {@link lightsearch.server.listener.LightSearchServerListener} по умолчанию:
 * {@link lightsearch.server.listener.LightSearchServerListenerSocketDefaultImpl}.
 *
 * @since 1.0
 * @author ViiSE
 * @version 1.0
 */
package lightsearch.server.initialization;
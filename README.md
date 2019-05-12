LightSearch
===========

LightSearch - комплекс программ, не зависимый от бизнес-логики предприятия, решающий задачи, необходимые для данного предприятия. На данный момент комплекс разрабатывается для предприятия, которому необходимы две функции: поиск товара по штрих-коду и создание мягкого чека.

Из каких программ состоит данный комплекс?
------------------------------------------
LightSearch состоит из четырех программ:

1) [LightSearchPC](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchPC.pdf) - работает под операционными системами GNU/Linux и Windows. Обеспечивает оперативный поиск по базе данных. 
 * Реализованы следующие функции:
   - Поиск по наименованию, по части наименования, или по штрих-коду;
   - Режим сканера и автоматического поиска;
   - Выбор поиска по подразделению;
   - Сохранение параметров подключения.
 * Что будет добавлено и изменено:
   - Создание перемещения;
   - Отделение программы от бизнес-логики;
   - Полная кросс-платформенность;
   - Улучшенная защита ini-файла.
  
2) [LightSearchAndroid](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchAndroid.pdf) - работает под операционной системой Android версии 4.4 и выше. Обеспечивает оперативный поиск по базе данных.
 * Реализованы следующие функции:
   -  Поиск по штрих-коду;
   -  Считывание штрих-кода через камеру смартфона;
   -  Выбор поиска по подразделению;
   -  Сохранение параметров подключения.
 * Что будет добавлено и изменено:
   - Создание мягкого чека.

3) [LightSearchServer](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchServer.pdf) - работает под операционными системами GNU/Linux и Windows. Обеспечивает связь между LightSearchAndroid и базой данных.
 * Реализованы следующие функции:
   - Общение между клиентом и сервером через JSON по принципу "команда-ответ";
   - Сохранение настроек, параметров базы данных и списка администраторов;
   - Логирование;
   - Черный список.
 * Что будет добавлено и изменено:
   - Создание обработчика мягкого чека;
   - Полная кросс-платформенность.

4) [LightSearchAdminPanel](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchAdminPanel.pdf) - работает под операционными системами GNU/Linux и Windows. Обеспечивает настройку и администрирование LightSearchServer.
 * Реализованы следующие функции:
   - Подключение к любому серверу LightSearchServer;
   - Установка таймаута клиента на сервере;
   - Установка времени перезагрузки сервера и непосредственно перезагрузка сервера;
   - Запрос списка клиентов;
   - Кик клиента;
   - Запрос черного списка;
   - Добавление и удаление клиента из черного списка;
   - Создание нового администратора;
   - Смена параметров базы данных.

История проекта будет описываться в документах, расположенных в папке Documents/Project history.

Используемые библиотеки
------------------------------------------
[JSON.simple] (https://github.com/fangyidong/json-simple)
[spots-dialog] (https://github.com/d-max/spots-dialog)
[Jaybird] (https://github.com/d-max/spots-dialog)
[ZXing] (https://github.com/zxing/zxing)

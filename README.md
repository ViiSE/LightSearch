<p align="center"> 
<img src="https://user-images.githubusercontent.com/43209824/64838878-905c6e00-d638-11e9-8026-e7b04d1af80f.png"
     width="300" height="300">
</p>

LightSearch
===========

LightSearch - комплекс программ, независимый от бизнес-логики предприятия. На данный момент комплекс разрабатывается для предприятия, которому необходимы две функции: поиск товара по штрих-коду и создание мягкого чека.

Из каких программ состоит данный комплекс?
------------------------------------------
LightSearch состоит из четырех программ:

1) [LightSearch PC](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchPC.pdf) - работает под операционными системами GNU/Linux и Windows. Обеспечивает оперативный поиск по базе данных. 
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
  
2) [LightSearch Android](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchAndroid.pdf) - работает под операционной системой Android версии 7.0 и выше. Обеспечивает оперативный поиск по базе данных.
 * Реализованы следующие функции:
   -  Поиск по штрих-коду;
   -  Считывание штрих-кода через камеру смартфона;
   -  Выбор поиска по подразделению;
   -  Сохранение параметров подключения;
   -  Создание мягкого чека;
   -  Автообновление через LightSearch Updater.

3) [LightSearch Server](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchServer.pdf) - работает под операционными системами GNU/Linux и Windows. Обеспечивает связь между LightSearchAndroid и базой данных.
 * Реализованы следующие функции:
   - Общение между клиентом и сервером через JSON по принципу "команда-ответ";
   - Сохранение настроек, параметров базы данных и списка администраторов;
   - Логирование;
   - Создание обработчика мягкого чека;
   - Черный список.
 * Что будет добавлено и изменено:
   - Полная кросс-платформенность.

4) [LightSearch Admin Panel](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchAdminPanel.pdf) - работает под операционными системами GNU/Linux и Windows. Обеспечивает настройку и администрирование LightSearchServer.
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

Используемые библиотеки, фреймворки и технологии
-------------------------------------------------
- [JSON.simple](https://github.com/fangyidong/json-simple)
- [spots-dialog](https://github.com/d-max/spots-dialog)
- [Jaybird](https://github.com/FirebirdSQL/jaybird)
- [ZXing](https://github.com/zxing/zxing)
- [AppUpdater](https://github.com/javiersantos/AppUpdater)
- [Spring Framework](https://github.com/spring-projects/spring-framework)
- [Spring Boot](https://github.com/spring-projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)
- [Spring Security](https://github.com/spring-projects/spring-security)
- [Vaadin](https://github.com/vaadin/)
- [JUnit 5](https://junit.org/junit5/)
- [TestNG](https://testng.org/doc/)
- [Mockito](https://github.com/mockito/mockito)

<p align="center"> 
<img src="https://user-images.githubusercontent.com/43209824/64838878-905c6e00-d638-11e9-8026-e7b04d1af80f.png"
     width="300" height="300">
</p>

LightSearch
===========

LightSearch - программный комплекс для решения задач по обеспечению мобильности сотрудников на предприятиях.

Из каких программ состоит данный комплекс?
------------------------------------------
LightSearch состоит из пяти программ:

1) [LightSearch PC](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchPC.pdf).
Обеспечивает оперативный поиск по базе данных.
 * Реализованы следующие функции:
   - Поиск по наименованию, по части наименования, или по штрих-коду;
   - Режим сканера и автоматического поиска;
   - Выбор поиска по подразделению;
   - Сохранение параметров подключения.
  
2) [LightSearch Android](https://github.com/ViiSE/LightSearch-Android) - работает под операционной системой Android версии 7.1 и выше.
 * Реализованы следующие функции:
   -  Поиск по наименованию, части наименования, штрих-коду, короткому штрих-коду, и по подразделению;
   -  Считывание штрих-кода через камеру смартфона;
   -  Привязка товара и проверка привязки товара;
   -  Мягкий чек;
   -  Автообновление.
 * [История](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchAndroid.pdf)
3) [LightSearch Server](https://github.com/ViiSE/LightSearch-Server) - RESTful сервер, обеспечивающий общение между клиентами LightSearch и программой, реализующей бизнес-логику, и реализующий необходимые функции для предприятия.
 * (Standard Edition) Реализованы следующие функции:
   - Общение между клиентом и сервером через JSON по принципу "команда-ответ";
   - Сохранение настроек, параметров базы данных и списка администраторов;
   - Логирование;
   - Создание обработчика мягкого чека;
   - Черный список.
 * (Spring Edition). LightSearch Server Spring Edition является RESTful сервером и поддерживает большинство фишек Spring'а.
 Более подробно читайте в [DevNotes](https://github.com/ViiSE/LightSearch/blob/master/Dev%20notes).
 * [История](https://github.com/ViiSE/LightSearch/blob/master/Documents/Project%20history/LightSearchServer.pdf)

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
   
5) [LightSearch Updater](https://github.com/ViiSE/LightSearch-Updater) - cервис для обновления LightSearch Android. Имеет web-панель администратора, при помощи которой можно создавать новые версии релиза LightSearch Android, добавлять через drag-and-drop apk файлы, и изменять файл релиза, который считывается LightSearch Android для проверки выхода нового релиза, в редакторе с поддержкой синтаксиса JSON. 

История проекта будет описываться в документах, расположенных в папке Documents/Project history.

Standard Edition теперь в [архивном репозитории](https://github.com/viise/LightSearch-Archive)
-----------------------------------------------------------------

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
- [React](https://github.com/facebook/react)
- [Thymeleaf](https://github.com/thymeleaf)
- [JsonWebToken](https://jwt.io)

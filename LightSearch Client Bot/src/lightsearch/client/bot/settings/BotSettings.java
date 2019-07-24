/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.settings;

import lightsearch.client.bot.TestCycle;

/**
 *
 * @author ViiSE
 */
public interface BotSettings {
    
    /*
        Задержка перед отправкой следующей команды
        
        Количество полных циклов прохождения команд
        
        Опционально - можно задавать свой собственный круг полных циклов. Для
        этого необходимо методу setTestCycle() передать экземпляр класса TestCycle.
        По умолчанию экземпляр класса TestCycle имеет реализацию по-умолчанию.
    */
    
    long delayBeforeSendingMessage();
    int amountCycle();
    void setTestCycle(TestCycle testCycle);
    TestCycle testCycle();
}

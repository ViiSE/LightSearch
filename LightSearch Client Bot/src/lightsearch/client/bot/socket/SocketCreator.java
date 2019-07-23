/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.socket;

import java.net.Socket;
import lightsearch.client.bot.exception.SocketException;

/**
 *
 * @author ViiSE
 */
public interface SocketCreator {
    Socket createSocket() throws SocketException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.data;

/**
 *
 * @author ViiSE
 */
public class ConnectionDTOInit {

    public static ConnectionDTO connectDTO(String ip, int port) {
        return new ConenctionDTODefaultImpl(ip, port);
    }
}

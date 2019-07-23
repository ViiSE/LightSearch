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
public class ConenctionDTODefaultImpl implements ConnectionDTO {
    
    private final String ip;
    private final int port;
    
    public ConenctionDTODefaultImpl(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String ip() {
        return ip;
    }

    @Override
    public int port() {
        return port;
    }
    
}

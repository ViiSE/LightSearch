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
public class ProductDTOInit {
    
    public static ProductDTO productDTO(String id, String amount) {
        return new ProductDTODefaultImpl(id, amount);
    }
}

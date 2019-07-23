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
public class ProductDTODefaultImpl implements ProductDTO {

    private final String id;
    private final String amount;
    
    public ProductDTODefaultImpl(String id, String amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String amount() {
        return amount;
    }
    
}

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
public class SearchDTODefaultImpl implements SearchDTO {

    private final String barcode;
    private final String sklad;
    private final String TK;
    
    public SearchDTODefaultImpl(String barcode, String sklad, String TK) {
        this.barcode = barcode;
        this.sklad   = sklad;
        this.TK      = TK;
    }

    @Override
    public String barcode() {
        return barcode;
    }

    @Override
    public String sklad() {
        return sklad;
    }

    @Override
    public String TK() {
        return TK;
    }
    
}

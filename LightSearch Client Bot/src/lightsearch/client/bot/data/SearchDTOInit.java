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
public class SearchDTOInit {

    public static SearchDTO searchDTO(String barcode, String sklad, String TK) {
        return new SearchDTODefaultImpl(barcode, sklad, TK);
    }
}

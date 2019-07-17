/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.config;

/**
 *
 * @author kaisar
 */
public interface GlobalVariables {

    //can be sent via rest endpoint to generate frontend validation.
    String REGX_FOR_UK_POST_CODE = "([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z]))))\\s[0-9][A-Za-z]{2})";
    String UNIT = "km"; //can be loaded from config or prop file in future
    public String DOWNLOAD_URL = "https://www.freemaptools.com/download/full-postcodes/ukpostcodes.zip"; //option to get the url from endpoint

}

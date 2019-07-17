/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.response;

import lombok.Data;
import nl.kaisar.geolocation.model.UKPostCode;
import nl.kaisar.geolocation.request.CoordinateRequest;

/**
 *
 * @author kaisar
 */
@Data
public class LocationRespose {

    String postCode;
    CoordinateRequest coordinate = new CoordinateRequest();

    public LocationRespose(UKPostCode postCode) {
        this.postCode = postCode.getPostcode();
        
        this.coordinate.setLatitude(postCode.getLatitude());
        this.coordinate.setLongitude(postCode.getLongitude());
    }

}

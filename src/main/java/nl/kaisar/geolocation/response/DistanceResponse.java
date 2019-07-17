/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.response;

import lombok.Data;
import nl.kaisar.geolocation.config.GlobalVariables;
import nl.kaisar.geolocation.model.UKPostCode;
import nl.kaisar.geolocation.services.DistanceService;

/**
 *
 * @author kaisar
 */
@Data
public class DistanceResponse {

    private LocationRespose locationA;
    private LocationRespose locationB;
    private double distance;
    private String unit = GlobalVariables.UNIT;

    public DistanceResponse(UKPostCode postCodeA, UKPostCode postCodeB) {
        if (postCodeA == null || postCodeB == null) {
            throw new RuntimeException("Post Code not found in DB, update the Database or add the postCode!!!");
        }
        this.locationA = new LocationRespose(postCodeA);
        this.locationB = new LocationRespose(postCodeB);
        DistanceService distanceService = new DistanceService();
        this.distance = distanceService.distance(locationA, locationB);
    }

}

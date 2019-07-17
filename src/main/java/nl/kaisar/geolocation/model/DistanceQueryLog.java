/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.model;

import lombok.Data;
import nl.kaisar.geolocation.response.DistanceResponse;

/**
 *
 * @author kaisar
 */
@Data
public class DistanceQueryLog implements Loggable {

    String postCodeA;
    String postCodeB;
    double distance;

    public DistanceQueryLog(DistanceResponse distanceResponse) {
        this.postCodeA = distanceResponse.getLocationA().getPostCode();
        this.postCodeB = distanceResponse.getLocationB().getPostCode();
        this.distance = distanceResponse.getDistance();
    }

}

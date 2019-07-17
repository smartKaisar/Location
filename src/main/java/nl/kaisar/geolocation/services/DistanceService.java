/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.services;

import nl.kaisar.geolocation.response.LocationRespose;

/**
 *
 * @author kaisar
 */
public class DistanceService {

    private final static double EARTH_RADIUS = 6371; // radius in kilometers

    public double distance(LocationRespose locationA, LocationRespose locationB) {
        return calculateDistance(locationA.getCoordinate().getLatitude(),
                locationA.getCoordinate().getLongitude(),
                locationB.getCoordinate().getLatitude(),
                locationB.getCoordinate().getLongitude());
    }

    private double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {

        // Using Haversine formula! See Wikipedia;
        double lon1Radians = Math.toRadians(longitude);

        double lon2Radians = Math.toRadians(longitude2);

        double lat1Radians = Math.toRadians(latitude);

        double lat2Radians = Math.toRadians(latitude2);

        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c);

    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));

    }

    private double square(double x) {
        return x * x;

    }

}

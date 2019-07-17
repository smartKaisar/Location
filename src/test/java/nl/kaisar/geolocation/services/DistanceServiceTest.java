/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.services;

import nl.kaisar.geolocation.model.UKPostCode;
import nl.kaisar.geolocation.response.LocationRespose;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kaisar
 */
public class DistanceServiceTest {

    public DistanceServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDistanceBoundary() {
        System.out.println("distance");
        UKPostCode ps1 = new UKPostCode();
        ps1.setPostcode("AA00 0AA");
        ps1.setLatitude(0);
        ps1.setLongitude(0);
        UKPostCode ps2 = new UKPostCode();
        ps2.setPostcode("BB11 1BB");
        ps2.setLatitude(0);
        ps2.setLongitude(0);
        LocationRespose locationA = new LocationRespose(ps1);
        LocationRespose locationB = new LocationRespose(ps2);
        DistanceService instance = new DistanceService();
        double expResult = 0.0;
        double result = instance.distance(locationA, locationB);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testDistanceReal() {
        System.out.println("distance");
        UKPostCode ps1 = new UKPostCode();
        ps1.setPostcode("AB10 1XG");
        ps1.setLatitude(57.14416516);
        ps1.setLongitude(-2.114847768);
        UKPostCode ps2 = new UKPostCode();
        ps2.setPostcode("AB15 6NA");
        ps2.setLatitude(57.151797);
        ps2.setLongitude(-2.185398);
        LocationRespose locationA = new LocationRespose(ps1);
        LocationRespose locationB = new LocationRespose(ps2);
        DistanceService instance = new DistanceService();
        double expResult = 4.339380896735677;
        double result = instance.distance(locationA, locationB);
        assertEquals(expResult, result, 0.0);
    }

}

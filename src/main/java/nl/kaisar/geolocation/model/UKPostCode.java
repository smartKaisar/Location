/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.model;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

/**
 *
 * @author kaisar
 */
@Data
public class UKPostCode {
    @Parsed
    String id;
    @Parsed
    String postcode;
    @Parsed
    double latitude;
    @Parsed
    double longitude;
    
}

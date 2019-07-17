/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.repo;

import nl.kaisar.geolocation.model.UKPostCode;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author kaisar
 */
public interface UKPostCodeRepo extends MongoRepository<UKPostCode, String> {

    public UKPostCode findOneByPostcode(String postcode);

}

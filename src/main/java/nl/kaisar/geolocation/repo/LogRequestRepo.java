/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.repo;

import nl.kaisar.geolocation.model.LogRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author kaisar
 */
public interface LogRequestRepo extends MongoRepository<LogRequest, String> {

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.model;

import lombok.Data;

/**
 *
 * @author kaisar
 */
@Data
public class PostCodeAddLog implements Loggable {

    private boolean isUpdate = false;
    private UKPostCode addedOrUpdated;

}

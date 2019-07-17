/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.request;

import javax.validation.constraints.Pattern;
import lombok.Data;
import nl.kaisar.geolocation.config.GlobalVariables;

/**
 *
 * @author kaisar
 */
@Data
public class DistanceQueryRequest {

    @Pattern(regexp = GlobalVariables.REGX_FOR_UK_POST_CODE, message = "Invalid UK post code!!!")
    private String postCodeA;
    @Pattern(regexp = GlobalVariables.REGX_FOR_UK_POST_CODE, message = "Invalid UK post code!!!")
    private String postCodeB;

}

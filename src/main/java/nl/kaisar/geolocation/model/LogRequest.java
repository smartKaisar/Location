/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.model;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpHeaders;

/**
 *
 * @author kaisar
 * @param <T>
 */
@Data
public class LogRequest<T extends Loggable> {

    @Id
    String id;
    String host;
    String ref;
    T logBody;
    Date time;

    public LogRequest(T logBody, HttpServletRequest request) {
        this.logBody = logBody;
        this.host = request.getRemoteAddr();
        this.ref = request.getHeader(HttpHeaders.REFERER);
        this.time = new Date();
    }

}

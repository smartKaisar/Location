/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import nl.kaisar.geolocation.model.DataFillLog;
import nl.kaisar.geolocation.model.DistanceQueryLog;
import nl.kaisar.geolocation.model.LogRequest;
import nl.kaisar.geolocation.model.PostCodeAddLog;
import nl.kaisar.geolocation.model.UKPostCode;
import nl.kaisar.geolocation.repo.LogRequestRepo;
import nl.kaisar.geolocation.repo.UKPostCodeRepo;
import nl.kaisar.geolocation.request.DistanceQueryRequest;
import nl.kaisar.geolocation.request.PostCodeRequest;
import nl.kaisar.geolocation.response.DistanceResponse;
import nl.kaisar.geolocation.response.LocationRespose;
import nl.kaisar.geolocation.services.CsvDataFillManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaisar
 */
@RestController
@RequestMapping("/postcode")
public class PostCode {

    @Autowired
    LogRequestRepo logRequestRepo;
    @Autowired
    UKPostCodeRepo postCodeRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostCode.class);

    @GetMapping(value = "/filldata")
    public String fillData(HttpServletRequest request) {
        LOGGER.debug("Fill data called...");
        DataFillLog fillLog = new DataFillLog();
        if (!CsvDataFillManager.getInstance().isInProgress()) {
            CsvDataFillManager.getInstance().fillData(null, postCodeRepo);
            fillLog.setMsg("Data will be filled!!!");
        } else {
            fillLog.setMsg("Process busy!!!");
        }

        LogRequest<DataFillLog> queryLog = new LogRequest<>(fillLog, request);
        logRequestRepo.save(queryLog);

        return fillLog.getMsg();

    }

    @RequestMapping(method = RequestMethod.POST, value = "/getDistance")
    public DistanceResponse getDistance(@RequestBody @Valid DistanceQueryRequest distanceQuery, HttpServletRequest request) {
        LOGGER.debug("Get Distance called...");
        if (postCodeRepo.count() < 1) {
            LOGGER.info("DB is not filled with Post Codes...");
            throw new RuntimeException("Data is not filled!!! call /filldata to populate the postcode DB");
        }
        DistanceResponse distanceResponse = new DistanceResponse(postCodeRepo.findOneByPostcode(distanceQuery.getPostCodeA()),
                postCodeRepo.findOneByPostcode(distanceQuery.getPostCodeB()));
        DistanceQueryLog distanceQueryLog = new DistanceQueryLog(distanceResponse);
        LogRequest<DistanceQueryLog> queryLog = new LogRequest<>(distanceQueryLog, request);
        logRequestRepo.save(queryLog);
        return distanceResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public LocationRespose addOrUpdatePostCode(@RequestBody @Valid PostCodeRequest postCodeRequest, HttpServletRequest request) {
        LOGGER.debug("Add or update called...");
        UKPostCode postCode = postCodeRepo.findOneByPostcode(postCodeRequest.getPostCode());
        PostCodeAddLog postCodeAddLog = new PostCodeAddLog();
        if (postCode != null) {
            LOGGER.info("found post code in DB and updating...");
            postCode.setLatitude(postCodeRequest.getCoordinate().getLatitude());
            postCode.setLongitude(postCodeRequest.getCoordinate().getLongitude());
            postCodeAddLog.setUpdate(true);
        } else {
            LOGGER.info("post code not found in DB and creating...");
            postCode = new UKPostCode();
            postCode.setPostcode(postCodeRequest.getPostCode());
            postCode.setLatitude(postCodeRequest.getCoordinate().getLatitude());
            postCode.setLongitude(postCodeRequest.getCoordinate().getLongitude());
            postCodeAddLog.setUpdate(false);
        }

        postCodeAddLog.setAddedOrUpdated(postCode);

        LogRequest<PostCodeAddLog> queryLog = new LogRequest<>(postCodeAddLog, request);
        logRequestRepo.save(queryLog);
        UKPostCode responsePostCode = postCodeRepo.save(postCode);
        LocationRespose respose = new LocationRespose(responsePostCode);
        return respose;
    }

}

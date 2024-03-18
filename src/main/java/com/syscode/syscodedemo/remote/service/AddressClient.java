package com.syscode.syscodedemo.remote.service;

import com.syscode.syscodedemo.remote.dto.StudentAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "address-service-api", url = "${app.address-service.api.url}")
public interface AddressClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{ownerId}", produces = "application/json")
    StudentAddress getAddressByOwnerId(@PathVariable("ownerId") Long ownerId);

}

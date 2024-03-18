package com.syscode.syscodedemo.remote.service;

import com.syscode.syscodedemo.remote.dto.StudentAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceAdapter {

    private final AddressClient addressClient;

    private static final StudentAddress NULL_ADDRESS =
            StudentAddress.builder().id(-1L).address("Address unavailable").build();

    /// TO-DO: catch and handle exception by type/code
    public StudentAddress getAddressByOwnerId(Long ownerId) {
        log.info("Getting address by owner id from Address Service");
        StudentAddress address;
        try {
            address = addressClient.getAddressByOwnerId(ownerId);
            log.info("Address returned by remote Address Service is {} for owner with id of {}", address, ownerId);
        } catch (Exception e) {
            log.error("Address Service is unavailable");
            address = NULL_ADDRESS;
        }
        return address;
    }

}

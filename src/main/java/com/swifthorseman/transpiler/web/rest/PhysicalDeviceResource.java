package com.swifthorseman.transpiler.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.swifthorseman.transpiler.domain.PhysicalDevice;
import com.swifthorseman.transpiler.repository.PhysicalDeviceRepository;
import com.swifthorseman.transpiler.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PhysicalDevice.
 */
@RestController
@RequestMapping("/api")
public class PhysicalDeviceResource {

    private final Logger log = LoggerFactory.getLogger(PhysicalDeviceResource.class);

    @Inject
    private PhysicalDeviceRepository physicalDeviceRepository;

    /**
     * POST  /physicalDevices -> Create a new physicalDevice.
     */
    @RequestMapping(value = "/physicalDevices",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhysicalDevice> createPhysicalDevice(@RequestBody PhysicalDevice physicalDevice) throws URISyntaxException {
        log.debug("REST request to save PhysicalDevice : {}", physicalDevice);
        if (physicalDevice.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new physicalDevice cannot already have an ID").body(null);
        }
        PhysicalDevice result = physicalDeviceRepository.save(physicalDevice);
        return ResponseEntity.created(new URI("/api/physicalDevices/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("physicalDevice", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /physicalDevices -> Updates an existing physicalDevice.
     */
    @RequestMapping(value = "/physicalDevices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhysicalDevice> updatePhysicalDevice(@RequestBody PhysicalDevice physicalDevice) throws URISyntaxException {
        log.debug("REST request to update PhysicalDevice : {}", physicalDevice);
        if (physicalDevice.getId() == null) {
            return createPhysicalDevice(physicalDevice);
        }
        PhysicalDevice result = physicalDeviceRepository.save(physicalDevice);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("physicalDevice", physicalDevice.getId().toString()))
                .body(result);
    }

    /**
     * GET  /physicalDevices -> get all the physicalDevices.
     */
    @RequestMapping(value = "/physicalDevices",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PhysicalDevice> getAllPhysicalDevices() {
        log.debug("REST request to get all PhysicalDevices");
        return physicalDeviceRepository.findAll();
    }

    /**
     * GET  /physicalDevices/:id -> get the "id" physicalDevice.
     */
    @RequestMapping(value = "/physicalDevices/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PhysicalDevice> getPhysicalDevice(@PathVariable Long id) {
        log.debug("REST request to get PhysicalDevice : {}", id);
        return Optional.ofNullable(physicalDeviceRepository.findOne(id))
            .map(physicalDevice -> new ResponseEntity<>(
                physicalDevice,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /physicalDevices/:id -> delete the "id" physicalDevice.
     */
    @RequestMapping(value = "/physicalDevices/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePhysicalDevice(@PathVariable Long id) {
        log.debug("REST request to delete PhysicalDevice : {}", id);
        physicalDeviceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("physicalDevice", id.toString())).build();
    }
}

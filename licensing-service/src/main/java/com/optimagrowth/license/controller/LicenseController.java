package com.optimagrowth.license.controller;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import com.optimagrowth.license.utils.UserContextHolder;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/v1/organization/{organizationId}/license")
public class LicenseController {

    private static final Logger logger = LoggerFactory.getLogger(LicenseController.class);

    @Autowired
    private LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) throws IllegalAccessException {

        License license = this.licenseService.getLicense(licenseId, organizationId);
        license.add(
                linkTo(methodOn(LicenseController.class)
                .getLicense(organizationId, license.getLicenseId()))
                .withSelfRel(),

                linkTo(methodOn(LicenseController.class)
                .createLicense(organizationId, license, null))
                .withRel("createLicense"),

                linkTo(methodOn(LicenseController.class)
                .updateLicense(organizationId, license))
                .withRel("updateLicense"),

                linkTo(methodOn(LicenseController.class)
                .deleteLicense(organizationId, license.getLicenseId()))
                .withRel("createLicense")
        );

        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request) {

        return ResponseEntity.ok(this.licenseService.updateLicense(request));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {

        return ResponseEntity.ok(this.licenseService.createLicense(request));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {

        return ResponseEntity.ok(this.licenseService.deleteLicence(licenseId));
    }

    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicenseWithClient(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @PathVariable("clientType") String clientType
    ) {
        return this.licenseService.getLicense(organizationId, licenseId, clientType);
    }

    @Timed
    @GetMapping(value = "/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) throws TimeoutException {
        logger.debug("LicenseServiceController Correlation id :{}", UserContextHolder.getContext().getCorrelationId());
        return this.licenseService.getLicensesByOrganization(organizationId);
    }
}

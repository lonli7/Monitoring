package com.optimagrowth.organization.controller;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService service;

    @GetMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId) {
        return ResponseEntity.ok(this.service.findById(organizationId));
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(
            @PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {
        this.service.update(organization);
    }

    @DeleteMapping(value = "/{organizationId}")
    public void deleteOrganization(@PathVariable("organizationId") String organizationId) {
        this.service.delete(organizationId);
    }
}

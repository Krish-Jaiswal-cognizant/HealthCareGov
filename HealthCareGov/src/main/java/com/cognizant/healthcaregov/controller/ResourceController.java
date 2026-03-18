package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.dto.ResourceDTO;
import com.cognizant.healthcaregov.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceDTO addResource(@RequestBody ResourceDTO resourceDTO) {
        return resourceService.addResource(resourceDTO);
    }

    @GetMapping
    public List<ResourceDTO> getAllResources(@RequestParam(required = false) Integer hospitalId) {
        if (hospitalId != null) {
            return resourceService.getResourcesByHospital(hospitalId);
        }
        return resourceService.getAllResources();
    }

    @GetMapping("/{id}")
    public ResourceDTO getResourceById(@PathVariable Integer id) {
        return resourceService.getResourceById(id);
    }

    @PutMapping("/{id}")
    public ResourceDTO updateResource(@PathVariable Integer id, @RequestBody ResourceDTO resourceDTO) {
        return resourceService.updateResource(id, resourceDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@PathVariable Integer id) {
        resourceService.deleteResource(id);
    }
}
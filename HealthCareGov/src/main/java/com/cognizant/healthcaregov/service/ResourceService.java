package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dao.HospitalRepository;
import com.cognizant.healthcaregov.dao.ResourceRepository;
import com.cognizant.healthcaregov.dto.ResourceDTO;
import com.cognizant.healthcaregov.entity.Hospital;
import com.cognizant.healthcaregov.entity.Resource;
import com.cognizant.healthcaregov.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public ResourceDTO addResource(ResourceDTO resourceDTO) {
        Hospital hospital = hospitalRepository.findById(resourceDTO.getHospitalID())
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + resourceDTO.getHospitalID()));

        Resource resource = new Resource();
        resource.setHospital(hospital);
        resource.setType(resourceDTO.getType());
        resource.setQuantity(resourceDTO.getQuantity());
        resource.setStatus(resourceDTO.getStatus());
        Resource savedResource = resourceRepository.save(resource);
        return convertToDTO(savedResource);
    }

    public List<ResourceDTO> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ResourceDTO> getResourcesByHospital(Integer hospitalId) {
        return resourceRepository.findByHospital_HospitalID(hospitalId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResourceDTO getResourceById(Integer id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        return convertToDTO(resource);
    }

    public ResourceDTO updateResource(Integer id, ResourceDTO resourceDTO) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

        if (resourceDTO.getHospitalID() != null) {
            Hospital hospital = hospitalRepository.findById(resourceDTO.getHospitalID())
                    .orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + resourceDTO.getHospitalID()));
            resource.setHospital(hospital);
        }
        resource.setType(resourceDTO.getType());
        resource.setQuantity(resourceDTO.getQuantity());
        resource.setStatus(resourceDTO.getStatus());
        Resource updatedResource = resourceRepository.save(resource);
        return convertToDTO(updatedResource);
    }

    public void deleteResource(Integer id) {
        if (!resourceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found with id: " + id);
        }
        resourceRepository.deleteById(id);
    }

    private ResourceDTO convertToDTO(Resource resource) {
        ResourceDTO dto = new ResourceDTO();
        dto.setResourceID(resource.getResourceID());
        dto.setHospitalID(resource.getHospital().getHospitalID());
        dto.setHospitalName(resource.getHospital().getName());
        dto.setType(resource.getType());
        dto.setQuantity(resource.getQuantity());
        dto.setStatus(resource.getStatus());
        return dto;


    }
}
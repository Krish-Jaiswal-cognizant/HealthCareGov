package com.cognizant.healthcaregov.service;

import com.cognizant.healthcaregov.dao.HospitalRepository;
import com.cognizant.healthcaregov.dto.HospitalDTO;
import com.cognizant.healthcaregov.entity.Hospital;
import com.cognizant.healthcaregov.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public HospitalDTO createHospital(HospitalDTO hospitalDTO) {
        Hospital hospital = new Hospital();
        hospital.setName(hospitalDTO.getName());
        hospital.setLocation(hospitalDTO.getLocation());
        hospital.setCapacity(hospitalDTO.getCapacity());
        hospital.setStatus(hospitalDTO.getStatus());
        Hospital savedHospital = hospitalRepository.save(hospital);
        return convertToDTO(savedHospital);
    }

    public List<HospitalDTO> getAllHospitals() {
        return hospitalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HospitalDTO getHospitalById(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + id));
        return convertToDTO(hospital);
    }

    public List<HospitalDTO> searchHospitals(String query) {
        return hospitalRepository.searchHospitals(query).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HospitalDTO updateHospital(Integer id, HospitalDTO hospitalDTO) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + id));
        hospital.setName(hospitalDTO.getName());
        hospital.setLocation(hospitalDTO.getLocation());
        hospital.setCapacity(hospitalDTO.getCapacity());
        hospital.setStatus(hospitalDTO.getStatus());
        Hospital updatedHospital = hospitalRepository.save(hospital);
        return convertToDTO(updatedHospital);
    }

    public void deleteHospital(Integer id) {
        if (!hospitalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hospital not found with id: " + id);
        }
        hospitalRepository.deleteById(id);
    }

    private HospitalDTO convertToDTO(Hospital hospital) {
        HospitalDTO dto = new HospitalDTO();
        dto.setHospitalID(hospital.getHospitalID());
        dto.setName(hospital.getName());
        dto.setLocation(hospital.getLocation());
        dto.setCapacity(hospital.getCapacity());
        dto.setStatus(hospital.getStatus());
        return dto;
    }
}
package com.cognizant.healthcaregov.controller;

import com.cognizant.healthcaregov.dto.HospitalDTO;
import com.cognizant.healthcaregov.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HospitalDTO createHospital(@RequestBody HospitalDTO hospitalDTO) {
        return hospitalService.createHospital(hospitalDTO);

    }

    @GetMapping
    public List<HospitalDTO> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/{id}")
    public HospitalDTO getHospitalById(@PathVariable Integer id) {
        return hospitalService.getHospitalById(id);

    }

    @GetMapping("/search")
    public List<HospitalDTO> searchHospitals(@RequestParam String query) {
        return hospitalService.searchHospitals(query);
    }

    @PutMapping("/{id}")
    public HospitalDTO updateHospital(@PathVariable Integer id, @RequestBody HospitalDTO hospitalDTO) {
        return hospitalService.updateHospital(id,hospitalDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable Integer id) {
        hospitalService.deleteHospital(id);
    }


}

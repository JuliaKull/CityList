package com.kull.citylist.controller;

import com.kull.citylist.dto.CityDto;
import com.kull.citylist.exception.CityException;
import com.kull.citylist.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;




@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/cities")
public class CityController {

    private final CityService cityService;

    @PostMapping("/add")
    public ResponseEntity<CityDto> addCityAndPhoto(@RequestBody CityDto cityDto) {
        log.info("REST request to add new city :{}", cityDto.getName());
        if (cityDto == null) {
            throw new CityException("City data are missing");
        }
        return ResponseEntity.ok().body(cityService.addCityAndPhoto(cityDto));
    }

    @PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
    @PutMapping("/update/{name}")
    public ResponseEntity<CityDto> updateCityAndPhoto(@PathVariable("name") String name, @RequestBody CityDto cityDto) {
        log.info("REST request to update the city :{}", name);
        if (cityDto == null) {
            throw new CityException("City data are missing");
        }
        return ResponseEntity.ok().body(cityService.updateCityAndPhoto(name, cityDto));
    }

    @GetMapping()
    public ResponseEntity<Page<CityDto>> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "12") int size) {
        log.info("REST request to get all cities");
        return ResponseEntity.ok().body(cityService.getAll(page, size));
    }

    @GetMapping("/{name}")
    public ResponseEntity<CityDto> findByName(@RequestBody CityDto cityDto) {
        log.info("REST request to get city :{}", cityDto);
        return ResponseEntity.ok().body(cityService.findByName(cityDto));
    }

}

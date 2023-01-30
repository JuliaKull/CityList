package com.kull.citylist.controller;

import com.kull.citylist.service.CsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/files")
public class CsvController {

   private final CsvService service;

    @PostMapping(value = "/upload")
    public void uploadCsv(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("REST request to upload new file :{}", file.getOriginalFilename());
        service.saveCsvFile(file);
    }


}

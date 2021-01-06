package com.ct.dataapi.controller;

import com.ct.dataapi.service.DataService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

//    @GetMapping(value = "/all-data", produces = "application/x-protobuf")
//    public ResponseEntity<Entity.Data> getAllData() {
//        Entity.Data data = dataService.findAllCountryCasesPerDateProtobuf();
//        return new ResponseEntity<>(data, HttpStatus.OK);
//    }
//
//    @GetMapping("test")
//    public List<Country> kek() {
////        return dataService.findStatesByCountryName("Australia");
////        return dataService.findCasesBetweenDates()
//        return dataService.findByLatitudeRange(40d, 70d);
//    }
}

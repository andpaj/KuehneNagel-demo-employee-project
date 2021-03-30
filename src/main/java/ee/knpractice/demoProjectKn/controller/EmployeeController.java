package ee.knpractice.demoProjectKn.controller;


import ee.knpractice.demoProjectKn.dto.EmployeeDto;
import ee.knpractice.demoProjectKn.model.request.EmployeeRequestModel;
import ee.knpractice.demoProjectKn.model.response.EmployeeRest;
import ee.knpractice.demoProjectKn.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600) // hack solution (the beset option is proxy)
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    final
    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{id}")
    private EmployeeRest getEmployeeById(@PathVariable long id){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        EmployeeRest employeeRest = modelMapper.map(employeeDto, EmployeeRest.class);

        return employeeRest;
    }


    @GetMapping(path = "/getAll")
    private ResponseEntity<List<EmployeeRest>> getAllEmployee(@RequestParam(required = false) String firstname){

        ModelMapper modelMapper = new ModelMapper();
        try {
            List<EmployeeDto> employeeDtos = new ArrayList<>();

            if (firstname == null) {
                employeeDtos = employeeService.getAllEmployees();
            } else {
                employeeDtos = employeeService.getAllEmployeesByFirstname(firstname);
            }

            if (employeeDtos.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<EmployeeRest> returnEmployeeList = new ArrayList<>();

            for (EmployeeDto employeeDto : employeeDtos) {
                EmployeeRest employeeRest = modelMapper.map(employeeDto, EmployeeRest.class);
                returnEmployeeList.add(employeeRest);
            }

            return new ResponseEntity<>(returnEmployeeList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @PostMapping(path = "/save")
    private ResponseEntity<EmployeeRest> saveEmployee(@RequestBody EmployeeRequestModel requestModel){

        ModelMapper modelMapper = new ModelMapper();
        EmployeeDto employeeDto = modelMapper.map(requestModel, EmployeeDto.class);

        try {
            EmployeeDto savedEmployeeDto = employeeService.saveEmployee(employeeDto);
            EmployeeRest employeeRest = modelMapper.map(savedEmployeeDto, EmployeeRest.class);

            return new ResponseEntity<>(employeeRest, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }





    }

    @PutMapping(path = "/update/{id}")
    private EmployeeRest updateEmployee(@PathVariable long id, @RequestBody EmployeeRequestModel requestModel){


        ModelMapper modelMapper = new ModelMapper();
        EmployeeDto employeeDto = modelMapper.map(requestModel, EmployeeDto.class);
        EmployeeDto savedEmployeeDto = employeeService.updateEmployee(id, employeeDto);
        EmployeeRest employeeRest = modelMapper.map(savedEmployeeDto, EmployeeRest.class);
        return employeeRest;


    }


    @DeleteMapping(path = "/delete/{id}")
    private ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping(path = "/deleteAll")
    private ResponseEntity<HttpStatus> deleteAllEmployees(){
        try {
            employeeService.deleteAllEmployees();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }







}

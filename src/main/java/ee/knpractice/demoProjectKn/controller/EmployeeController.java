package ee.knpractice.demoProjectKn.controller;


import ee.knpractice.demoProjectKn.DateValidator;
import ee.knpractice.demoProjectKn.dto.EmployeeDto;
import ee.knpractice.demoProjectKn.model.request.EmployeeRequestModel;
import ee.knpractice.demoProjectKn.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    final
    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{id}")
    private EmployeeDto getEmployeeById(@PathVariable long id){

        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        return employeeDto;
    }


    @GetMapping(path = "/getAll")
    private ResponseEntity<List<EmployeeDto>> getAllEmployee(@RequestParam(required = false) String firstname){

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


            return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @PostMapping(path = "/save")
    private ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeRequestModel requestModel) throws Exception {

        //check if date is valid
        String requestDate = requestModel.getHireDate();
        DateValidator validator = new DateValidator("dd/MM/yyyy");

        if (!validator.isValid(requestDate)){
            throw new Exception("The date format should be dd/MM/yyyy");
        }

        // if date is valid convert it to Date.class
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("EST"));
        Date savedDate = dateFormatter.parse(requestDate);


        //set EmployeeDto with data
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstname(requestModel.getFirstname());
        employeeDto.setLastname(requestModel.getLastname());
        employeeDto.setEmail(requestModel.getEmail());
        employeeDto.setActive(requestModel.isActive());
        employeeDto.setTelephone(requestModel.getTelephone());
        employeeDto.setHireDate(savedDate);

        //Pass EmployeeDto to service
        try {
            EmployeeDto savedEmployeeDto = employeeService.saveEmployee(employeeDto);

            return new ResponseEntity<>(savedEmployeeDto, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(path = "/update/{id}")
    private EmployeeDto updateEmployee(@PathVariable long id, @RequestBody EmployeeRequestModel requestModel) throws Exception {

        //check if date is valid
        String requestDate = requestModel.getHireDate();
        DateValidator validator = new DateValidator("dd/MM/yyyy");

        if (!validator.isValid(requestDate)){
            throw new Exception("The date format should be dd/MM/yyyy");
        }

        // if date is valid convert it to Date.class
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("EST"));
        Date savedDate = dateFormatter.parse(requestDate);


        //set EmployeeDto with data
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstname(requestModel.getFirstname());
        employeeDto.setLastname(requestModel.getLastname());
        employeeDto.setEmail(requestModel.getEmail());
        employeeDto.setActive(requestModel.isActive());
        employeeDto.setTelephone(requestModel.getTelephone());
        employeeDto.setHireDate(savedDate);

        EmployeeDto savedEmployeeDto = employeeService.updateEmployee(id, employeeDto);

        return savedEmployeeDto;


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

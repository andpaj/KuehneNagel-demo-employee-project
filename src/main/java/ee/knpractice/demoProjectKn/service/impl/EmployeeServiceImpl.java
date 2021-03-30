package ee.knpractice.demoProjectKn.service.impl;

import ee.knpractice.demoProjectKn.dto.EmployeeDto;
import ee.knpractice.demoProjectKn.entity.EmployeeEntity;
import ee.knpractice.demoProjectKn.exception.ServiceException;
import ee.knpractice.demoProjectKn.model.response.ErrorMessages;
import ee.knpractice.demoProjectKn.repository.EmployeeRepository;
import ee.knpractice.demoProjectKn.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    final
    EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<EmployeeDto> getAllEmployees() {

        ModelMapper modelMapper = new ModelMapper();

        List<EmployeeDto> employeeDtosList = new ArrayList<>();

        List<EmployeeEntity> employeeEntitiesList = employeeRepository.findAll();

        for (EmployeeEntity employeeEntity: employeeEntitiesList){
            EmployeeDto employeeDto = modelMapper.map(employeeEntity, EmployeeDto.class);
            employeeDtosList.add(employeeDto);
        }

        return employeeDtosList;


    }

    @Override
    public List<EmployeeDto> getAllEmployeesByFirstname(String firstname) {
        ModelMapper modelMapper = new ModelMapper();

        List<EmployeeDto> employeeDtosList = new ArrayList<>();

        List<EmployeeEntity> employeeEntitiesList = employeeRepository.findByFirstnameContaining(firstname);

        for (EmployeeEntity employeeEntity: employeeEntitiesList){
            EmployeeDto employeeDto = modelMapper.map(employeeEntity, EmployeeDto.class);
            employeeDtosList.add(employeeDto);
        }

        return employeeDtosList;
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        ModelMapper modelMapper = new ModelMapper();

        EmployeeEntity employeeEntity = employeeRepository.findById(id);
        if (employeeEntity == null) throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        EmployeeDto employeeDto = modelMapper.map(employeeEntity, EmployeeDto.class);

        return employeeDto;



    }

    @Override
    public EmployeeDto updateEmployee(long id, EmployeeDto employeeDto) {
        ModelMapper modelMapper = new ModelMapper();

        EmployeeEntity employeeEntity = employeeRepository.findById(id);
        if (employeeEntity == null) throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        employeeEntity.setFirstname(employeeDto.getFirstname());
        employeeEntity.setLastname(employeeDto.getLastname());
        employeeEntity.setTelephone(employeeDto.getTelephone());
        employeeEntity.setEmail(employeeDto.getEmail());
        employeeEntity.setHireDate(employeeDto.getHireDate());
        employeeEntity.setActive(employeeDto.isActive());

        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
        EmployeeDto returnDtoValue = modelMapper.map(savedEntity, EmployeeDto.class);

        return returnDtoValue;


    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        ModelMapper modelMapper = new ModelMapper();

        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);

        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);

        EmployeeDto returnEmployeeDto = modelMapper.map(savedEntity, EmployeeDto.class);


        return returnEmployeeDto;

    }

    @Override
    public void deleteEmployee(long id) {

        EmployeeEntity employeeEntity = employeeRepository.findById(id);
        if (employeeEntity == null) throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        employeeRepository.delete(employeeEntity);


    }

    @Override
    public void deleteAllEmployees() {

        employeeRepository.deleteAll();

    }
}

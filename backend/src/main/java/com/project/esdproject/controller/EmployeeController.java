package com.project.esdproject.controller;

import com.project.esdproject.model.Employees;
import com.project.esdproject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<Employees> addEmp(@RequestBody Employees employee) {
        if (employee.getEmpDepartment().getDepartmentID() != 2) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Employees savedEmployee = employeeService.addEmp(employee);
        if (savedEmployee != null) {
            return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }

    // working
    @GetMapping("/get")
    public ResponseEntity<List<Employees>> getAllEmployees() {
        List<Employees> emp = employeeService.getEmployeeList();
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }
}

package com.project.esdproject.Service;

import com.project.esdproject.model.Employees;
import com.project.esdproject.Repository.EmployeeRepository; // Assuming you have a Spring Data JPA repository
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employees> getEmployeeList() {
        return employeeRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Employees addEmp(Employees employee) {
        try {
            String binaryImageString = employee.getPhoto_path();
            String curr_email = employee.getEmail();

            employee.setPhoto_path("");
            entityManager.persist(employee);

            TypedQuery<Employees> query = entityManager.createQuery("SELECT e FROM Employees e WHERE e.email = :curr_email", Employees.class);
            query.setParameter("curr_email", curr_email);
            employee = query.getSingleResult();

            String fileName = employee.getEmployee_id().toString() + ".txt";
            File file = new File(fileName);

            if (file.createNewFile()) {
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(binaryImageString);

                    employee.setPhoto_path(fileName);
                    entityManager.merge(employee);

                    return employee;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                System.out.println("File already exists.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

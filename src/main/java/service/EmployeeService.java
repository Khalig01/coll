package service;

import exceptions.EmplooyeException;
import exceptions.EmplooyeeStorageFullException;
import exceptions.EmployeeAlreadyAddedException;
import homework.coll.Employee;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
public class EmployeeService {
    private static final int MAX_COUNT = 10;
    private final List<Employee> employees = new ArrayList<>(MAX_COUNT);

    public void add(String fistName, String lastName) {
        if (employees.size() >= MAX_COUNT) {
            throw new EmplooyeeStorageFullException();
        }
        Employee employee = new Employee(fistName, lastName);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.add(employee);
    }

    public void remove(String fistName, String lastName) {
        var employee= new Employee(fistName,lastName);
        if(!employees.contains(employee)){
            throw new EmplooyeException();
        }
        employees.remove(employee);
    }

    public Employee find(String fistName, String lastName) {
        for (Employee employee : employees) {
            if (employee.getFirstName().equals(fistName) && employee.getLastName().equals(lastName)) {
                return employee;
            }
        }
            throw new EmplooyeException();
        }


        public Collection<Employee> getAll() {
            return Collections.unmodifiableCollection(employees);
        }
    }




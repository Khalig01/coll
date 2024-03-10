package service;

import exceptions.*;
import homework.coll.Employee;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmployeeService {
    private static final int MAX_COUNT = 10;
    private final Map<String, Employee> employees = new HashMap<>(MAX_COUNT);


    public void add(String firstName, String lastName,int salary,int department) throws EmployeeAlreadyAddedException {
        checkLetters(firstName,lastName);
        if (employees.size() >= MAX_COUNT) {
            throw new EmplooyeeStorageFullException();
        }
        Employee employee = new Employee(StringUtils.capitalize(firstName),
                StringUtils.capitalize(lastName),salary, department);
        var key = makeKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }

        employees.put(key, employee);

    }

    public void remove(String fistName, String lastName) {
        var key = makeKey(fistName, lastName);
        var removed = employees.remove(key);
        if (removed == null) {
            throw new EmployeeNotFoundException("Error");
        }
    }

    public Employee find(String firstName, String lastName) {
        var key = makeKey(firstName, lastName);
        var employee = employees.get(key);
        if (employee == null) {
            throw new EmployeeNotFoundException("Error");
        }
        return employee;
    }


    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private static String makeKey(String firstName, String lastName) {
        return (firstName + "_" + lastName).toLowerCase();
    }
private static void checkLetters(String... words){
        for(String word : words){
            if(!StringUtils.isAlpha(word)){
                throw new WrongNameException("Name of last name must contain only letters");
            }
        }
}

}




package service;

import exceptions.EmplooyeException;
import exceptions.EmplooyeeStorageFullException;
import exceptions.EmployeeAlreadyAddedException;
import homework.coll.Employee;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmployeeService {
    private static final int MAX_COUNT = 10;
    private final Map<String, Employee> employees = new HashMap<>(MAX_COUNT);


    public void add(String fistName, String lastName) throws EmployeeAlreadyAddedException {
        if (employees.size() >= MAX_COUNT) {
            throw new EmplooyeeStorageFullException();
        }
        Employee employee = new Employee(fistName, lastName);
        var key = makeKey(fistName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }

        employees.put(key, employee);

    }

    public void remove(String fistName, String lastName) {
        var key = makeKey(fistName, lastName);
        var removed = employees.remove(key);
        if (removed == null) {
            throw new EmplooyeException();
        }
    }

    public Employee find(String fistName, String lastName) {
        var key = makeKey(fistName, lastName);
        var employee = employees.get(key);
        if (employees != null) {
            return employee;
        }
        throw new EmplooyeException();
    }


    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private static String makeKey(String firstName, String lastName) {
        return (firstName + "_" + lastName).toLowerCase();
    }
}




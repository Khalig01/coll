package service;

import exceptions.*;
import homework.coll.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();

    @Test
    void testAdd() {
        employeeService.add("Iqor", "KUPRIN", 1000, 1);
        employeeService.add("IvAN", "VirZa", 2000, 2);


        var actualFirst = employeeService.find("Iqor", "KUPRIN");
        assertThat(actualFirst).isNotNull();
        assertThat(actualFirst.getFirstName()).isEqualTo("Iqor");
        assertThat(actualFirst.getLastName()).isEqualTo("KUPRIN");
        assertThat(actualFirst.getDepartment()).isEqualTo(1);
        assertThat(actualFirst.getSalary()).isEqualTo(1_000);

        var actualSecond = employeeService.find("IvAN", "VirZa");
        assertThat(actualSecond).isNotNull();
        assertThat(actualSecond.getFirstName()).isEqualTo("IvAN");
        assertThat(actualSecond.getLastName()).isEqualTo("VirZa");
        assertThat(actualSecond.getDepartment()).isEqualTo(2);
        assertThat(actualSecond.getSalary()).isEqualTo(2_000);
    }

    @Test
    void testAddDuplicate() {
        employeeService.add("iqor", "kuprin", 1000, 1);
        assertThrows(EmployeeAlreadyAddedException.class,() -> employeeService.add("iqor",
                "kuprin", 2000, 2));
    }

    @Test
    void testFull(){
        employeeService.add("iqor", "kuprin", 1000, 1);
        employeeService.add("nikita", "kuprin", 1000, 1);
        employeeService.add("sonya", "kuprin", 1000, 1);
        employeeService.add("zaxar", "kuprin", 1000, 1);
        employeeService.add("roma", "kuprin", 1000, 1);
        employeeService.add("nastya", "kuprin", 1000, 1);
        employeeService.add("eqor", "kuprin", 1000, 1);
        employeeService.add("misha", "kuprin", 1000, 1);
        employeeService.add("vlad", "kuprin", 1000, 1);
        employeeService.add("valeriy", "kuprin", 1000, 1);
        assertThrows(EmplooyeeStorageFullException.class,() -> employeeService.add("iqora",
                "kuprin", 2000, 2));
    }
    @Test
    void testWrongName(){
        assertThrows(WrongNameException.class,() -> employeeService.add("1iqor678", "678kuprin9", 1000, 1));
        assertThrows(WrongNameException.class,() -> employeeService.add("1iqora1", "1kuprin", 1000, 1));
        assertThrows(WrongNameException.class,() -> employeeService.add("555iqora", "kuprin555", 1000, 1));
    }
    @Test
    void testGetAll(){
        employeeService.add("iqor", "kuprin", 1000, 1);
        employeeService.add("nikita", "kuzya", 2000, 2);
        var actual= employeeService.getAll();
        assertThat(actual).containsExactlyInAnyOrder(
                new Employee("Iqor", "Kuprin", 1000, 1),
                new Employee("Nikita", "Kuzya", 2000, 2));
    }
@Test
    void testNotFound(){
    assertThrows(EmployeeNotFoundException.class,() -> employeeService.find("meni","bill"));
}
@Test
    void testRemove(){
    assertThrows(EmployeeNotFoundException.class,() -> employeeService.remove("meni","bill"));
    employeeService.add("Iqor","Kuprin",1000,1);
    employeeService.add("Zaxar","Osman",2000,2);
    var actual= employeeService.find("Iqor","Kuprin");
    assertThat(actual).isNotNull();
    employeeService.remove("Iqor","Kuprin");
    assertThrows(EmployeeNotFoundException.class,() -> employeeService.find("Iqor","Kuprin"));
}

}




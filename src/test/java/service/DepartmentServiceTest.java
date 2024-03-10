package service;

import exceptions.EmplooyeException;
import exceptions.EmployeeNotFoundException;
import homework.coll.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    EmployeeService employeeService;
    @InjectMocks
    DepartmentService departmentService;
    @BeforeEach
    void setUp(){

        var employees= List.of(
                new Employee("Nastya","Nastya", 10000,1),
                new Employee("Natasha","Natasha",  2000,2),
                new Employee("Maks","Maks", 30000,3),
                new Employee("Kristina","Kristina",4000,1),
        new Employee("Sveta","Sveta",2100,2),
        new Employee("Roza","Roza",1200,2));

        when(employeeService.getAll()).thenReturn(employees);
    }
@Test
    void testDepartmentMaxSallary(){
    assertThat(departmentService.findMaxSalary(1)).isEqualTo(new Employee("Kristina","Kristina", 4000,1));
    assertThat(departmentService.findMaxSalary(1)).isEqualTo(new Employee("Nastya","Nastya", 10000,1));
    assertThrows(EmployeeNotFoundException.class,() -> departmentService.findMaxSalary(1));

}
    @Test
void testDepartmentMinSallary(){
        var actual1= departmentService.findMinSalary(1);
    assertThat(actual1.isPresent()).isTrue();
    assertThat(actual1.get()).isEqualTo(new Employee("Nastya","Nastya", 10000,1));

    var actual2= departmentService.findMinSalary(3);
    assertThat(actual2.isPresent()).isTrue();
    assertThat(actual2.get()).isEqualTo(new Employee("Maks","Maks", 30000,3));
    assertThrows(EmployeeNotFoundException.class,() -> departmentService.findMinSalary(1111));
var actual3= departmentService.findMinSalary(1111);
assertThat(actual3.isEmpty()).isTrue();
}
@Test
    void testFindByDepartment(){
        var actual= departmentService.findByDepartment(2);
        assertThat(actual).containsExactlyInAnyOrder(
                new Employee("Natasha","Natasha",  2000,2),
               new Employee("Sveta","Sveta",2100,2),
        new Employee("Roza","Roza",1200,2));
}
@Test
    void testGroupByDepartment(){
        var actual= departmentService.groupByDepartment();
          var expected=Map.of(

                1,List.of(new Employee("Nastya","Nastya",10000,1)
                        ,new Employee("Kristina","Kristina",4000,1)),
                2,List.of(new Employee("Natasha","Natasha",  2000,2),
                        new Employee("Sveta","Sveta",2100,2),
                        new Employee("Roza","Roza",1200,2)),
                    3,List.of(new Employee("Maks","Maks", 30000,3)));

          assertThat(actual).isEqualTo(expected);



}
}
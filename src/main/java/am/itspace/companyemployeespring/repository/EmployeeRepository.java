package am.itspace.companyemployeespring.repository;

import am.itspace.companyemployeespring.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}

package com.javadevta.employeetracker.service;

import com.javadevta.employeetracker.entity.Employee;
import com.javadevta.employeetracker.entity.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> getEmployeeById(Long id);

    Optional<Employee> getEmployeeByEmail(String email);

    List<Employee> getEmployeesByLastnameIgnoreCase(String lastname);

    List<Employee> getEmployeesByPhoneNumberStartingWith(String prefix);

    List<Employee> getEmployeesByGender(Gender gender);

    List<Employee> getEmployeesByProjectName(String projectName);

    List<Employee> getEmployeesByProjectId(Long projectId);

    List<Employee> getEmployeesByManagerId(Long managerId);

    List<Employee> getEmployeesBySkillName(String skillName);

    List<Employee> getEmployeesCreatedBetween(LocalDateTime start, LocalDateTime end);

    Page<Employee> getEmployeesCreatedBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<Employee> getEmployeeBySalaryAccountNumber(String accountNumber);

    List<Employee> getTopEmployeesByLastnameAsc(int limit);

    boolean existsByEmail(String email);

    long countEmployeesByGender(Gender gender);

    Page<Employee> getAllEmployees(Pageable pageable);

    List<Employee> getEmployeesWithAllSkills(List<String> skills);

    List<Employee> getEmployeesWithoutProject();

    List<Employee> getEmployeesByManagerAndSkill(Long managerId, String skillName);

    List<Employee> getTopEmployeesByProject(Long projectId, int limit);

    List<Employee> getRecentEmployeesBySkill(String skillName, int days);

    List<Object[]> getEmployeeCountByProject();

    List<Employee> getEmployeesByCityAndProject(String city, String projectName);

    List<Employee> getEmployeesWithSkillRatingThreshold(byte minRating, long minSkills);

    List<Employee> getEmployeesWithProjectLongerThanMonths(int months);
}

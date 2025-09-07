package com.javadevta.employeetracker.service;

import com.javadevta.employeetracker.entity.Employee;
import com.javadevta.employeetracker.entity.Gender;
import com.javadevta.employeetracker.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email.trim().toLowerCase());
    }

    @Override
    public List<Employee> getEmployeesByLastnameIgnoreCase(String lastname) {
        return employeeRepository.findByLastnameIgnoreCase(lastname.trim());
    }

    @Override
    public List<Employee> getEmployeesByPhoneNumberStartingWith(String prefix) {
        return employeeRepository.findByPhoneNumberStartingWith(prefix.trim());
    }

    @Override
    public List<Employee> getEmployeesByGender(Gender gender) {
        return employeeRepository.findByGender(gender);
    }

    @Override
    public List<Employee> getEmployeesByProjectName(String projectName) {
        return employeeRepository.findByProject_Name(projectName.trim());
    }

    @Override
    public List<Employee> getEmployeesByProjectId(Long projectId) {
        return employeeRepository.findByProject_Id(projectId);
    }

    @Override
    public List<Employee> getEmployeesByManagerId(Long managerId) {
        return employeeRepository.findByManager_Id(managerId);
    }

    @Override
    public List<Employee> getEmployeesBySkillName(String skillName) {
        return employeeRepository.findBySkills_Name(skillName.trim());
    }

    @Override
    public List<Employee> getEmployeesCreatedBetween(LocalDateTime start, LocalDateTime end) {
        return employeeRepository.findByCreatedAtBetween(start, end);
    }

    @Override
    public Page<Employee> getEmployeesCreatedBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return employeeRepository.findByCreatedAtBetween(start, end, pageable);
    }

    @Override
    public Optional<Employee> getEmployeeBySalaryAccountNumber(String accountNumber) {
        return employeeRepository.findBySalaryBank_AccountNumber(accountNumber.trim());
    }

    @Override
    public List<Employee> getTopEmployeesByLastnameAsc(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("lastname").ascending());
        return employeeRepository.findAllByOrderByLastnameAsc(pageable);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    public long countEmployeesByGender(Gender gender) {
        return employeeRepository.countByGender(gender);
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public List<Employee> getEmployeesWithAllSkills(List<String> skills) {
        return employeeRepository.findEmployeesWithAllSkills(skills, skills.size());
    }

    @Override
    public List<Employee> getEmployeesWithoutProject() {
        return employeeRepository.findEmployeesWithoutProject();
    }

    @Override
    public List<Employee> getEmployeesByManagerAndSkill(Long managerId, String skillName) {
        return employeeRepository.findEmployeesByManagerAndSkill(managerId, skillName);
    }

    @Override
    public List<Employee> getTopEmployeesByProject(Long projectId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return employeeRepository.findTopEmployeesByProject(projectId, pageable);
    }

    @Override
    public List<Employee> getRecentEmployeesBySkill(String skillName, int days) {
        LocalDateTime fromDate = LocalDateTime.now().minusDays(days);
        return employeeRepository.findRecentEmployeesBySkill(skillName, fromDate);
    }

    @Override
    public List<Object[]> getEmployeeCountByProject() {
        return employeeRepository.countEmployeesByProject();
    }

    @Override
    public List<Employee> getEmployeesByCityAndProject(String city, String projectName) {
        return employeeRepository.findEmployeesByCityAndProject(city, projectName);
    }

    @Override
    public List<Employee> getEmployeesWithSkillRatingThreshold(byte minRating, long minSkills) {
        return employeeRepository.findEmployeesWithSkillRatingThreshold(minRating, minSkills);
    }

    @Override
    public List<Employee> getEmployeesWithProjectLongerThanMonths(int months) {
        LocalDate cutoff = LocalDate.now().minusMonths(months);
        return employeeRepository.findEmployeesWithProjectStartOnOrBefore(cutoff);
    }

}

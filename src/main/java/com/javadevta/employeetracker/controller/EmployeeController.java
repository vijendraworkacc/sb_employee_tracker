package com.javadevta.employeetracker.controller;

import com.javadevta.employeetracker.entity.Gender;
import com.javadevta.employeetracker.mapper.EmployeeMapper;
import com.javadevta.employeetracker.response.EmployeeResponse;
import com.javadevta.employeetracker.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(path = "api/employee")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    // GET /api/employee/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> ResponseEntity.status(HttpStatus.OK).body(employeeMapper.toResponse(employee)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET /api/employee?email=someone@example.com
    @GetMapping
    public ResponseEntity<EmployeeResponse> getEmployeeByEmail(@RequestParam String email) {
        return employeeService.getEmployeeByEmail(email)
                .map(employee -> ResponseEntity.status(HttpStatus.OK).body(employeeMapper.toResponse(employee)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET /api/employee/lastname?lastname=singh
    @GetMapping("/lastname")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByLastname(@RequestParam String lastname) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByLastnameIgnoreCase(lastname)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/phone?prefix=987
    @GetMapping("/phone")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByPhonePrefix(@RequestParam String prefix) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByPhoneNumberStartingWith(prefix)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/gender?gender=MALE
    @GetMapping("/gender")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByGender(@RequestParam Gender gender) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByGender(gender)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/project?name=BankingSystem
    @GetMapping("/project")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByProjectName(@RequestParam String name) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByProjectName(name)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/project/{id}
    @GetMapping("/project/{id}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByProjectId(@PathVariable Long id) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByProjectId(id)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/manager/{id}
    @GetMapping("/manager/{id}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByManager(@PathVariable("id") Long managerId) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByManagerId(managerId)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/skill?name=Java
    @GetMapping("/skill")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesBySkillName(@RequestParam String name) {
        List<EmployeeResponse> employees = employeeService.getEmployeesBySkillName(name)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/created?start=...&end=...
    @GetMapping("/created")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesCreatedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        if (start.isAfter(end)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<EmployeeResponse> result = employeeService.getEmployeesCreatedBetween(start, end)
                .stream().map(employeeMapper::toResponse).toList();
        return result.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // GET /api/employee/created/page?start=...&end=...&page=0&size=10
    @GetMapping("/created/page")
    public ResponseEntity<Page<EmployeeResponse>> getEmployeesCreatedBetweenPaged(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Pageable pageable) {
        if (start.isAfter(end)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Page<EmployeeResponse> page = employeeService.getEmployeesCreatedBetween(start, end, pageable)
                .map(employeeMapper::toResponse);
        return page.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(page);
    }

    // GET /api/employee/salarybank?accountNumber=123
    @GetMapping("/salarybank")
    public ResponseEntity<EmployeeResponse> getEmployeeBySalaryAccountNumber(@RequestParam String accountNumber) {
        return employeeService.getEmployeeBySalaryAccountNumber(accountNumber)
                .map(emp -> ResponseEntity.status(HttpStatus.OK).body(employeeMapper.toResponse(emp)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET /api/employee/top?limit=5
    @GetMapping("/top")
    public ResponseEntity<List<EmployeeResponse>> getTopEmployeesByLastname(@RequestParam int limit) {
        if (limit <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<EmployeeResponse> employees = employeeService.getTopEmployeesByLastnameAsc(limit)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/exists?email=john.doe@example.com
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.existsByEmail(email));
    }

    // GET /api/employee/count?gender=MALE
    @GetMapping("/count")
    public ResponseEntity<Long> countByGender(@RequestParam Gender gender) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.countEmployeesByGender(gender));
    }

    // GET /api/employee/page?page=0&size=5
    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(Pageable pageable) {
        Page<EmployeeResponse> page = employeeService.getAllEmployees(pageable)
                .map(employeeMapper::toResponse);
        return page.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(page);
    }

    // GET /api/employee/skills/all?names=Java,Spring Boot
    @GetMapping("/skills/all")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesWithAllSkills(@RequestParam List<String> names) {
        List<EmployeeResponse> employees = employeeService.getEmployeesWithAllSkills(names)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/noproject
    @GetMapping("/noproject")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesWithoutProject() {
        List<EmployeeResponse> employees = employeeService.getEmployeesWithoutProject()
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/manager/{id}/skill?name=Kafka
    @GetMapping("/manager/{id}/skill")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByManagerAndSkill(
            @PathVariable Long id, @RequestParam String name) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByManagerAndSkill(id, name)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/project/{id}/top?limit=3
    @GetMapping("/project/{id}/top")
    public ResponseEntity<List<EmployeeResponse>> getTopEmployeesByProject(
            @PathVariable Long id, @RequestParam(defaultValue = "5") int limit) {
        List<EmployeeResponse> employees = employeeService.getTopEmployeesByProject(id, limit)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/recent?skill=Spring Boot&days=30
    @GetMapping("/recent")
    public ResponseEntity<List<EmployeeResponse>> getRecentEmployeesBySkill(
            @RequestParam String skill, @RequestParam int days) {
        List<EmployeeResponse> employees = employeeService.getRecentEmployeesBySkill(skill, days)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/stats/project
    @GetMapping("/stats/project")
    public ResponseEntity<List<Map<String, Object>>> countEmployeesByProject() {
        List<Map<String, Object>> result = employeeService.getEmployeeCountByProject()
                .stream()
                .map(row -> Map.of("projectName", row[0], "count", row[1]))
                .toList();
        return result.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // GET /api/employee/search?city=Bengaluru&project=BankingSystem
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByCityAndProject(
            @RequestParam String city, @RequestParam String project) {
        List<EmployeeResponse> employees = employeeService.getEmployeesByCityAndProject(city, project)
                .stream().map(employeeMapper::toResponse).toList();
        return employees.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    // GET /api/employee/skills/threshold?minRating=4&minSkills=3
    @GetMapping("/skills/threshold")
    public ResponseEntity<List<EmployeeResponse>> employeesWithSkillRatingThreshold(
            @RequestParam byte minRating, @RequestParam long minSkills) {
        List<EmployeeResponse> result = employeeService.getEmployeesWithSkillRatingThreshold(minRating, minSkills)
                .stream().map(employeeMapper::toResponse).toList();
        return result.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // GET /api/employee/project/longer-than?months=6
    @GetMapping("/project/longer-than")
    public ResponseEntity<List<EmployeeResponse>> employeesWithProjectLongerThan(@RequestParam int months) {
        if (months <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        List<EmployeeResponse> result = employeeService.getEmployeesWithProjectLongerThanMonths(months)
                .stream().map(employeeMapper::toResponse).toList();
        return result.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

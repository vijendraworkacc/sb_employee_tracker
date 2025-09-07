package com.javadevta.employeetracker.repository;

import com.javadevta.employeetracker.entity.Employee;
import com.javadevta.employeetracker.entity.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String lowerCase);

    List<Employee> findByLastnameIgnoreCase(String lastname);

    List<Employee> findByPhoneNumberStartingWith(String prefix);

    List<Employee> findByGender(Gender gender);

    List<Employee> findByProject_Name(String projectName);

    List<Employee> findByProject_Id(Long projectId);

    List<Employee> findByManager_Id(Long managerId);

    List<Employee> findBySkills_Name(String skillName);

    List<Employee> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Employee> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<Employee> findBySalaryBank_AccountNumber(String accountNumber);

    List<Employee> findAllByOrderByLastnameAsc(Pageable pageable);

    boolean existsByEmail(String email);

    long countByGender(Gender gender);

    @Query("""
       SELECT e 
       FROM Employee e 
       JOIN e.skills s 
       WHERE s.name IN :skills 
       GROUP BY e 
       HAVING COUNT(DISTINCT s) = :skillCount
       """)
    List<Employee> findEmployeesWithAllSkills(@Param("skills") List<String> skills,
                                              @Param("skillCount") long skillCount);

    @Query("SELECT e FROM Employee e WHERE e.project IS NULL")
    List<Employee> findEmployeesWithoutProject();

    @Query("""
       SELECT e 
       FROM Employee e 
       JOIN e.skills s 
       WHERE e.manager.id = :managerId AND s.name = :skillName
       """)
    List<Employee> findEmployeesByManagerAndSkill(@Param("managerId") Long managerId,
                                                  @Param("skillName") String skillName);

    @Query("""
       SELECT DISTINCT e 
       FROM Employee e 
       JOIN e.skills s 
       WHERE e.project.id = :projectId 
       ORDER BY s.ratingOutOf5 DESC
       """)
    List<Employee> findTopEmployeesByProject(@Param("projectId") Long projectId, Pageable pageable);

    @Query("""
       SELECT e 
       FROM Employee e 
       JOIN e.skills s 
       WHERE s.name = :skillName AND e.createdAt >= :fromDate
       """)
    List<Employee> findRecentEmployeesBySkill(@Param("skillName") String skillName,
                                              @Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT e.project.name, COUNT(e) FROM Employee e GROUP BY e.project.name")
    List<Object[]> countEmployeesByProject();

    @Query("""
       SELECT e 
       FROM Employee e 
       JOIN e.addresses a 
       WHERE a.city = :city AND e.project.name = :projectName
       """)
    List<Employee> findEmployeesByCityAndProject(@Param("city") String city,
                                                 @Param("projectName") String projectName);

    @Query("""
           SELECT e
           FROM Employee e
           JOIN e.skills s
           WHERE s.ratingOutOf5 >= :minRating
           GROUP BY e
           HAVING COUNT(s) >= :minSkills
           """)
    List<Employee> findEmployeesWithSkillRatingThreshold(@Param("minRating") byte minRating,
                                                         @Param("minSkills") long minSkills);

    @Query("""
           SELECT e
           FROM Employee e
           WHERE e.project IS NOT NULL
             AND e.project.mapStartDate <= :cutoff
           """)
    List<Employee> findEmployeesWithProjectStartOnOrBefore(@Param("cutoff") LocalDate cutoff);

    @Query("""
           SELECT e
           FROM Employee e
           JOIN e.skills s
           WHERE e.project.id = :projectId
           GROUP BY e
           ORDER BY AVG(s.ratingOutOf5) DESC
           """)
    List<Employee> findTopByProjectOrderByAvgSkillDesc(@Param("projectId") Long projectId,
                                                       Pageable pageable);
}
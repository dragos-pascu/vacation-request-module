package com.project.vacationapi.repository;
import com.project.vacationapi.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    @Query(value = "SELECT SUM(DATEDIFF('day', vr.start_date, vr.end_date) + 1) FROM vacation_requests vr WHERE vr.employee_id = :employeeId AND YEAR(vr.start_date) = :year", nativeQuery = true)
    Integer countUsedVacationDaysByEmployeeId(Long employeeId, Integer year);


}
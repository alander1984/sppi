package com.egartech.sppi.repo;

import com.egartech.sppi.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessRepository extends JpaRepository<Process, Long>, JpaSpecificationExecutor<Process> {

    @Query(value = "select count(*) from sppi_processes where extract(day from date_end) = :currentDay and extract(month from date_end) = :currentMonth" +
            " and extract(year from date_end) = :currentYear",
            nativeQuery = true)
    int findProcessCountForCurrentDay(@Param("currentDay") int currentDay,
                                      @Param("currentMonth") int currentMonth,
                                      @Param("currentYear") int currentYear);
}

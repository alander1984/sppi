package com.egartech.sppi.repo;

import com.egartech.sppi.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessRepository extends JpaRepository<Process, Long>, JpaSpecificationExecutor<Process> {

    @Query(value = "select count(*) from process where extract(day from date_end) = :currentDayOfMonth",
            nativeQuery = true)
    int findProcessCountForCurrentDay(@Param("currentDayOfMonth") int currentDayOfMonth);
}

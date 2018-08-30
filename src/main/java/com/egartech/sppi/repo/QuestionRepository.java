package com.egartech.sppi.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.egartech.sppi.model.*;


public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question>{
}

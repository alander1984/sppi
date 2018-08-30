package com.egartech.sppi.specification;

import com.egartech.sppi.model.*;
import org.springframework.data.jpa.domain.Specification;
import com.egartech.sppi.model.Question_;

public class QuestionSpecification {
    public static Specification<Question> byCode(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Question_.code), "%" + query + "%");
    }
}

package com.egartech.sppi.specification;

import com.egartech.sppi.model.*;
import org.springframework.data.jpa.domain.Specification;
import com.egartech.sppi.model.Question_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class QuestionSpecification {
    public static Specification<Question> byCode(final String code) {
        return new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get(Question_.code), code);
            }
        };
    }
}

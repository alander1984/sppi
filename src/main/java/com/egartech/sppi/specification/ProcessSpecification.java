package com.egartech.sppi.specification;

import com.egartech.sppi.model.Process_;
import com.egartech.sppi.model.Question;
import com.egartech.sppi.model.Question_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProcessSpecification {
    public static Specification<com.egartech.sppi.model.Process> paused() {
        return new Specification<com.egartech.sppi.model.Process>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Process_.isFinished), false);
            }
        };
    }

    public static Specification<com.egartech.sppi.model.Process> completed() {
        return new Specification<com.egartech.sppi.model.Process>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Process_.isFinished), true);
            }
        };
    }

}

package org.example.specification;

import org.example.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TaskSpecification implements Specification<TaskEntity> {

    private final SearchCriteria criteria;

    public TaskSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Specification<TaskEntity> and(Specification<TaskEntity> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<TaskEntity> or(Specification<TaskEntity> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate
            (Root<TaskEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
    }


package com.comitfy.healtie.util.common;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpecification<Entity extends BaseEntity> implements Specification<Entity> {

    private List<SearchCriteria> criterias;

    public List<SearchCriteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<SearchCriteria> criterias) {
        this.criterias = criterias;
    }

    @Override
    public Predicate toPredicate
            (Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : criterias) {
            Predicate predicate = null;
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.greaterThanOrEqualTo(

                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("lang")) {
                predicate = builder.equal(
                        root.<String>get(criteria.getKey()), LanguageEnum.valueOf((String) criteria.getValue()));
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    predicate = builder.like(
                            builder.lower(root.<String>get(criteria.getKey())), "%" + criteria.getValue() + "%");

                } else {
                    predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            } else if (criteria.getOperation().equalsIgnoreCase("=")) {
                predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
            } else {
                continue;
            }

            predicates.add(predicate);

        }

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));

    }


   /* @Override
    public Predicate toPredicate
            (Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(

                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }*/
}

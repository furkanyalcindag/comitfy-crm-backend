package com.comitfy.crm.userModule.specification;

import com.comitfy.crm.userModule.entity.Role;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.util.common.BaseSpecification;
import org.springframework.stereotype.Component;
import com.comitfy.crm.util.common.SearchCriteria;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserSpecification extends BaseSpecification<User> {


    private List<SearchCriteria> criterias;

    public List<SearchCriteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<SearchCriteria> criterias) {
        this.criterias = criterias;
    }

    @Override
    public Predicate toPredicate
            (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : criterias) {
            Predicate predicate = null;
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.greaterThanOrEqualTo(

                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    predicate = builder.like(
                            builder.lower(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");

                } else {
                    predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            } else if (criteria.getOperation().equalsIgnoreCase("=")) {


                if (criteria.getKey().equals("uuid")) {

                    Join<Role, User> userRole = root.join("roles");
                    predicate = builder.equal(userRole.get(criteria.getKey()), UUID.fromString(criteria.getValue().toString()));

                    //predicate = root.join("roles")..builder.equal(root.get(criteria.getKey()), criteria.getValue());
                } else {
                    predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }


            } else {
                continue;
            }

            predicates.add(predicate);

        }

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));

    }


}
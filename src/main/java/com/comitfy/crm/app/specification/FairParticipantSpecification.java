package com.comitfy.crm.app.specification;

import com.comitfy.crm.app.entity.FairParticipant;
import com.comitfy.crm.app.model.enums.LanguageEnum;
import com.comitfy.crm.util.common.BaseSpecification;
import com.comitfy.crm.util.common.SearchCriteria;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class FairParticipantSpecification extends BaseSpecification<FairParticipant> {


    private List<SearchCriteria> criterias;

    public List<SearchCriteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<SearchCriteria> criterias) {
        this.criterias = criterias;
    }

    @Override
    public Predicate toPredicate
            (Root<FairParticipant> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : criterias) {
            Predicate predicate = null;
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.greaterThanOrEqualTo(

                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("lang")) {
                predicate = builder.equal(
                        root.<String>get(criteria.getKey()), LanguageEnum.valueOf(criteria.getValue().toString()));
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
                if(criteria.getKey().equals("fair_id")){
                    predicate = builder.equal(root.get("fair").get("id"), criteria.getValue());
                }
                else
                    predicate = builder.equal(root.get(criteria.getKey()), criteria.getValue());
            } else {
                continue;
            }

            predicates.add(predicate);

        }

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));

    }
}

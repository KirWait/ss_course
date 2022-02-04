package org.example.specification;

import org.example.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskSpecificationBuilder {

    private final List<SearchCriteria> params;

    public TaskSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public void with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));

    }

    public Specification<TaskEntity> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<TaskEntity>> specs = params.stream()
                .map(TaskSpecification::new)
                .collect(Collectors.toList());

        Specification<TaskEntity> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
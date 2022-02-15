package org.example.filters;

import lombok.Data;

@Data
public class ProjectFilter {

    private boolean isDeleted;
    private int page;
    private int pageSize;
}

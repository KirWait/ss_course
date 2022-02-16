package org.example.filters;

import lombok.Data;

@Data
public class TaskFilter {

    private int page;
    private int pageSize;
    private String search;
}

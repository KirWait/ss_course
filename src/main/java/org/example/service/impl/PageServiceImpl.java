package org.example.service.impl;

import org.example.exception.PageException;
import org.example.service.PageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

@Service
public class PageServiceImpl implements PageService {
    @Override
    public <T> List<T> findAllByPage(int page, int pageSize, List<T> list) throws PageException {
        int listSize = list.size();
        if (pageSize <= 0) throw new PageException("Page size must be greater than 0!");
        if (page <= 0) page = 1;
        double maxPage = ceil((double)listSize / (double)pageSize);
        if (maxPage < page) throw new PageException(String.format("No such page: max page is %.0f!", maxPage));
        List<T> pageableList = new ArrayList<>();
        if (listSize <= pageSize) return list;
        else {
            int pageStartIndex = pageSize * page - (pageSize - 1);
            for (int i = 1; i <= listSize; i++) {
                if (i >= pageStartIndex && i < pageStartIndex + pageSize ) {
                    pageableList.add(list.get(i - 1));
                }
            }
        }
        return pageableList;
    }
}

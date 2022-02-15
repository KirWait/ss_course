package org.example.service;

import org.example.exception.PageException;

import java.util.List;

public interface PageService {

    <T> List<T> findAllByPage(int page, int pageSize, List<T> list) throws PageException;
}

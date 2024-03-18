package com.syscode.syscodedemo.rest.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageResponse<T> {

    private final long total;
    private final long pageNumber;
    private final long pageSize;
    private final List<T> content;


    public PageResponse(List<T> content, long pageNumber, long pageSize, long total) {
        this.total = total;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.content = new ArrayList<>(content);
    }

}

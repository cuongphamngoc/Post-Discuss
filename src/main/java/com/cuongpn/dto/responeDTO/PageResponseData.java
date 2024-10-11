package com.cuongpn.dto.responeDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class PageResponseData<T> extends ResponseData<T> {
    private int pageSize;
    private int pageNum;
    private long totalElements;

    public PageResponseData(int status, String message, T data, int pageNum,int pageSize,long totalElements) {
        super(status, message,data);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
}

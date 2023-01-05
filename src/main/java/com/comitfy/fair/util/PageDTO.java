package com.comitfy.fair.util;

import com.comitfy.fair.util.common.BaseDTO;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class PageDTO<DTO extends BaseDTO>{
    private int number; //page number
    private int size; //
    private Sort sort;
    private int totalPage; //toplamsayfalama
    private Long totalElements; //toplam kayıt sayısı
    private List<DTO> data;

    public void setStart(Page page, List<DTO> list){
        this.number=page.getNumber();
        this.size=page.getSize();
        this.sort=page.getSort();
        this.totalPage=page.getTotalPages();
        this.totalElements=page.getTotalElements();
        this.data=list;


    }
}

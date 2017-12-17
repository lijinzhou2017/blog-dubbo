package com.blog.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Transient
    private Integer pageNum = 1;
    @Transient
    private Integer pageSize = 10;
}

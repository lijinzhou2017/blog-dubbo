package com.blog.helper;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台数据表格插件 bootsrtap table  数据格式构造
 *
 * @author lijinzhou
 * @since 2017/12/20 19:31
 */
@Data
public class BootstrapTableEntity<T> implements Serializable {

    private Long total = 0L;
    private List<T> rows = new ArrayList<>();

    public BootstrapTableEntity(PageInfo<T> pageInfo) {
        this.total = pageInfo.getTotal();
        this.rows = pageInfo.getList();
    }

}

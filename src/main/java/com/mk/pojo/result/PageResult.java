package com.mk.pojo.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long total;
    private List<T> rows;
    public PageResult (IPage<T> page) {
        this.total = page.getTotal();
        this.rows = page.getRecords();
    }
}

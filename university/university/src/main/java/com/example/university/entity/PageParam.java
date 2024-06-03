package com.example.university.entity;

import lombok.Data;

import java.util.HashMap;

@Data
public class PageParam {
    // 默认值
    private static int PAGE_SIZE = 20;
    private static int PAGE_NUM = 1;
    // 参数
    private int pageSize = PAGE_SIZE;
    private int pageNum = PAGE_NUM;
    // 给一个初始值，避免出现空指针异常
    private HashMap param = new HashMap();
}

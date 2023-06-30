package com.seeleaf.guitarbackend.common;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageVo {
    long current = 1;
    long pageSize = 10;
    long total = 0;
    List data = new ArrayList();
}

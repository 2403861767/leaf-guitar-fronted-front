package com.seeleaf.guitarbackend.common;

import com.seeleaf.guitarbackend.exception.BusinessException;

public class CommonFunctions {
    public static void validateParamIsNull(Object obj){
        if (obj == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误!");
        }
    }
}

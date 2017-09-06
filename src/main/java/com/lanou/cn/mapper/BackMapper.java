package com.lanou.cn.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lanou on 2017/8/1.
 */
public interface BackMapper {
    List<Map<String, Object>> backThing(Map<String, Object> params);
    Map<String,Object> findOnlyOrdDtlInfo(Map<String, Object> params);
    Map<String,Object> back(Map<String, Object> params);
    void returnsOrder(@Param("ordDtlNo") String ordDtlNo, @Param("ordNo") String ordNo);
    List<Map<String, Object>> prdCountOrDtlNo(Map<String, Object> params);
}

package com.enshub.mapper;

import com.enshub.uncompress.People;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by szty on 2018/10/12.
 */
@Mapper
public interface PeopleMapper {
    void batchUpdate(List<People> peoples);
}

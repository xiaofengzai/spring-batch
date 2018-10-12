package com.enshub.uncompress;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Created by szty on 2018/10/10.
 */
public class ReadFileMapper implements FieldSetMapper<People> {

    @Override
    public People mapFieldSet(FieldSet fieldSet) throws BindException {
        People people = new People();
        people.setUsername(fieldSet.readString(0));
        people.setAge(fieldSet.readInt(1));
        people.setAddress(fieldSet.readString(2));
        people.setBirthday(fieldSet.readDate(3));
        return people;
    }
}
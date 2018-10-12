package com.enshub.uncompress;

import com.enshub.mapper.PeopleMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by szty on 2018/10/10.
 */
public class FileWriter implements ItemWriter<People> {
    private static final String INSERT_PRODUCT = "INSERT INTO people (username, age, address, birthday) VALUES(?, ?, ?, ?)    ON DUPLICATE KEY UPDATE address=VALUES(`address`); ";

    private JdbcTemplate jdbcTemplate;

    private PeopleMapper peopleMapper;

    public FileWriter(DataSource dataSource,PeopleMapper peopleMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.peopleMapper=peopleMapper;
    }

    @Override
    public void write(List<? extends People> items) throws Exception {
   //    jdbcTemplate.batchUpdate(INSERT_PRODUCT, transformToObjects(items));
//        for (People people : items) {
//            jdbcTemplate.update(INSERT_PRODUCT, people.getUsername(), people.getAge(), people.getAddress(), people.getBirthday());
//        }

        peopleMapper.batchUpdate((List<People>)items);
    }





    private List<Object[]> transformToObjects(List<? extends People> peoples) {
        return peoples.parallelStream().map(people->new Object[]{people.getUsername(), people.getAge(), people.getAddress(), people.getBirthday()}).collect(Collectors.toList());
    }
}

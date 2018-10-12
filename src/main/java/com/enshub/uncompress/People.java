package com.enshub.uncompress;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by szty on 2018/10/10.
 */
@Data
@Entity
public class People implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private int age;
    @Column
    private String address;
    @Column
    private Date birthday;
}

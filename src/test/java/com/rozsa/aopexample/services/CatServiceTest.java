package com.rozsa.aopexample.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CatServiceTest {

    @Autowired
    CatsServiceEx01 catsServiceEx01;

    @Test
    void catServiceExe01() {

    }
}

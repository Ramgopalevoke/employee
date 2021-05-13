package com.evoke.employee;

import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class BeforeAllTest {



    @BeforeAll
    public static void init() {
        System.out.println("Before All init() method called");
    }
}

package com.example.ybase;

import com.ybase.common.YDate;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        String str = YDate.formatDate(new Date(), YDate.FORMAT_DATE_SIMPLE);
        System.out.println( str );
    }

    @Test
    public void getAge() throws Exception {
        int age = YDate.getAge("2017-01-10");
    }
}
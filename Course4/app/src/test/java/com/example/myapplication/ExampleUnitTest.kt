package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest{
    @Test
    fun testSum(){
        val result = calculate("3","4") {a,b -> a+b}
        assertEquals("7", result);
    }

    @Test
    fun testDivision(){
        val result = calculate("2","0")
            {a,b -> if (b==0) null else a/b}
            assertNull(result)
    }

    @Test
    fun testInvalidInput() {
        val result = calculate("a", "2") { a, b -> a + b }
        assertEquals("Invalid input", result)
    }
}
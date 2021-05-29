package com.mfahimi.numbertextwatcher

import org.junit.Test

import org.junit.Assert.*
import java.lang.IllegalArgumentException

class StringFormatterTest {

    @Test
    fun formatString() {
        assertEquals("123,234", StringFormatter.formatString("123234"))
        assertEquals("1,234", StringFormatter.formatString("1234"))
        assertEquals("1,234.5667", StringFormatter.formatString("1234.5667"))
        assertEquals("234.5667", StringFormatter.formatString("234.5667"))
    }

    @Test
    fun removeNonNumeric() {
        assertEquals("1234", StringFormatter.removeNonNumeric("1,234"))
        assertEquals("234.5667", StringFormatter.removeNonNumeric("234.5667"))
        assertEquals("1234.5667", StringFormatter.removeNonNumeric("1,234.5667"))
    }
}
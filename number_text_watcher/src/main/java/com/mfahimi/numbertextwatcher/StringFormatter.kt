package com.mfahimi.numbertextwatcher

import java.lang.IllegalArgumentException
import kotlin.math.floor

object StringFormatter {

    fun formatString(input: String, separator: Char = ',', splitSize: Int = 3): String {
        var decimal = ""
        val trimInput = if (input.contains(".")) {
            decimal = input.split(".")[1]
            input.split(".")[0]
        } else input

        if (trimInput.length < splitSize) return input
        val numberLength = trimInput.length
        var howManySeparators = floor(((numberLength - 1) / splitSize.toDouble())).toInt()
        var formattedString = trimInput.substring(0, (numberLength - (howManySeparators * splitSize)))
        while (howManySeparators > 0) {
            formattedString = formattedString + separator + trimInput.substring(
                (numberLength - (howManySeparators * splitSize)),
                (numberLength - ((howManySeparators - 1) * splitSize))
            )
            howManySeparators -= 1
        }
        if (decimal.isEmpty())
            return formattedString

        return formattedString.plus(".").plus(decimal)
    }

    fun removeNonNumeric(numberString: String): String {
        val strings = StringBuilder()
        for (i in numberString) {
            if (i.isDigit()) {
                strings.append(i.toString().toInt())
            } else if (i == '.') {
                strings.append(i.toString())
            }
        }
        return strings.toString()
    }
}
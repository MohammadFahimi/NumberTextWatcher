package com.mfahimi.numbertextwatcher

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

/**
 * Your [EditText] should have ltr layout direction
 */
class NumberTextWatcher(private val edView: EditText) : TextWatcher {
    private var beforeText: String = ""

    override fun afterTextChanged(s: Editable?) {
    }

    /*
     * start: is the start index of the red highlighted text (that is about to be deleted)
     * count: is the length of the red highlighted text (that is about to be deleted)
     * after: is the length of the green highlighted text (that is about to be added)
     * */
    override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
        beforeText = p0.toString()
    }

    /*
    * start: is the start index of the green highlighted text (that just got added).
    *        This is the same as the start of beforeTextChanged.
    * before: is the length of the red highlighted text (that just got deleted).
    *         This is the same as the count of beforeTextChanged.
    * count: is the length of the green highlighted text (that just got added).
    *        This is the same as the after of beforeTextChanged.
    * */
    override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
        if (p0.isNullOrEmpty()) {
            return
        }
        if (p0.last() == '.')
            return

        // 1. get cursor position : p0 = start + before
        val initialCursorPosition = start + before

        val targetString = if (isSeparatorRemoved(p0)) {
            StringBuilder(p0).deleteCharAt(initialCursorPosition - 2).toString()
        } else {
            p0
        }
        //2. get digit count after cursor position : c0
        val numOfDigitsToRightOfCursor = getNumberOfDigits(
            beforeText.substring(
                initialCursorPosition,
                beforeText.length
            )
        )
        val newAmount = formatAmount(targetString.toString())

        edView.removeTextChangedListener(this)
        if (newAmount.toString() == "00") {
            edView.setText("0")
            beforeText = "0"
            edView.setSelection(1)
        } else {
            edView.setText(newAmount, TextView.BufferType.SPANNABLE)
            //set new cursor position
            edView.setSelection(getNewCursorPosition(numOfDigitsToRightOfCursor, newAmount.toString()))
        }
        edView.addTextChangedListener(this)
    }

    private fun isSeparatorRemoved(p0: CharSequence): Boolean {
        val beforeDigit = getNumberOfDigits(beforeText.toDigitPart())
        val newDigit = getNumberOfDigits(p0.toString().toDigitPart())
        return newDigit == beforeDigit && p0 != "00"
    }

    private fun formatAmount(amount: String): SpannableStringBuilder {
        val sNonNumeric = StringFormatter.removeNonNumeric(amount)
        return formattedSpannablePrice(sNonNumeric)
    }

    private fun formattedSpannablePrice(amount: String): SpannableStringBuilder {
        val price = StringFormatter.formatString(amount)
        return SpannableStringBuilder(price)
    }

    private fun getNewCursorPosition(digitCountToRightOfCursor: Int, numberString: String): Int {
        var position = 0
        var c = digitCountToRightOfCursor
        for (i in numberString.reversed()) {
            if (c == 0)
                break

            if (i.isDigit())
                c--
            position++
        }
        return numberString.length - position
    }

    private fun getNumberOfDigits(text: String): Int {
        var count = 0
        for (i in text)
            if (i.isDigit())
                count++
        return count
    }

    private fun String.toDigitPart(): String {
        return StringFormatter.removeNonNumeric(this)
    }

}
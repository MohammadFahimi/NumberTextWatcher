package com.mfahimi.numbertextwatcher.sample

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.mfahimi.numbertextwatcher.NumberTextWatcher
import com.mfahimi.numbertextwatcher.StringFormatter
import com.mfahimi.numbertextwatcher.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNumber.addTextChangedListener(NumberTextWatcher(binding.etNumber))

        binding.etNumber.addTextChangedListener {
            binding.tvResult.text = StringFormatter.removeNonNumeric(it.toString())
        }
    }
}
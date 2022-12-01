package com.app.cointcoins

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.app.cointcoins.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cbCoins.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.lyCoins.visibility = View.GONE;
            } else {
                binding.lyCoins.visibility = View.VISIBLE;
            }
        }

        binding.cbBills.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.lyBills.visibility = View.GONE;
            } else {
                binding.lyBills.visibility = View.VISIBLE;
            }
        }

        binding.btnCount.setOnClickListener {
            var total = 0.0
            var convertToEuros = 0.0
            if (binding.lyCoins.visibility == View.VISIBLE) {
                val allCentsAndFigures = mapOf(
                    binding.cen1.editText?.text to 1.0,
                    binding.cen2.editText?.text to 2.0,
                    binding.cen5.editText?.text to 5.0,
                    binding.cen10.editText?.text to 10.0,
                    binding.cen20.editText?.text to 20.0,
                    binding.cen50.editText?.text to 50.0
                )
                allCentsAndFigures.map { (v, n) ->
                    val etAmount = v.toString()
                    total += checkThings(etAmount.isNotEmpty(), etAmount, n)
                }
                convertToEuros = total / 100
            }

            if (binding.lyBills.visibility == View.VISIBLE) {
                val allEurosAndFigures = mapOf(
                    binding.eu1.editText?.text to 1.0,
                    binding.eu2.editText?.text to 2.0,
                    binding.eu5.editText?.text to 5.0,
                    binding.eu10.editText?.text to 10.0,
                    binding.eu20.editText?.text to 20.0,
                    binding.eu50.editText?.text to 50.0,
                    binding.eu100.editText?.text to 100.0,
                    binding.eu200.editText?.text to 200.0
                )

                allEurosAndFigures.map { (v, n) ->
                    val etAmount = v.toString()
                    convertToEuros += checkThings(etAmount.isNotEmpty(), etAmount, n)
                }
            }

            binding.total.text = "Euros: ${convertToEuros}€"
        }

    }

    private fun checkThings(notEmpty: Boolean, total: String, i: Double): Double {
        Log.v("MyApp", total)
        return when (notEmpty) {
            true -> total.toDouble() * i
            else -> 0.0
        }
    }
}
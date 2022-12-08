package com.app.alphavlaka

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.app.alphavlaka.adapters.DatePickerAdapter
import com.app.alphavlaka.adapters.TimePickerAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout

class LarderFormActivity : AppCompatActivity() {
    private lateinit var testGroupChip: ChipGroup
    private lateinit var textInputTime: TextInputLayout

    private var titleOrNull: String? = null
    private var operations: Int = 0
    private lateinit var etTime: EditText
    private lateinit var etDate: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larder_form)


        textInputTime = findViewById(R.id.textInputLayout)
        etTime = findViewById(R.id.timeFormat)
        etTime.setOnClickListener { showTimePickerDialog() }


        etDate = findViewById(R.id.dateFormat)
        etDate.setOnClickListener { showDatePickerDialog() }

        setupChip()


        val stock = findViewById<TextView>(R.id.productStock)


        val swMaterial = findViewById<SwitchMaterial>(R.id.swMaterial)


        swMaterial.setOnCheckedChangeListener { _, b ->
            if (b) {
                textInputTime.visibility = View.VISIBLE
            } else {
                textInputTime.visibility = View.INVISIBLE
            }
        }


        val increment = findViewById<ImageView>(R.id.incrementStock)
        val decrement = findViewById<ImageView>(R.id.decrementStock)


        increment.setOnClickListener {
            operations = stock.text.toString().toInt() + 1
            stock.text = operations.toString()
        }
        decrement.setOnClickListener {
            if (stock.text.toString().toInt() != 1) {
                operations = stock.text.toString().toInt() - 1
                stock.text = operations.toString()
            }
        }

        val btnSave = findViewById<Button>(R.id.btn_save)

        btnSave.setOnClickListener {
            testGroupChip.children
                .forEach {
                    if ((it as Chip).isChecked) {
                        titleOrNull = it.text.toString()
                    }
                }
            if (titleOrNull == null) {
                titleOrNull = "Others"
            }
            Log.v("MyApp", "$titleOrNull")
        }
    }

    private fun setupChip() {
        testGroupChip = findViewById(R.id.testChipGroup)
        val nameList =
            arrayListOf(
                "Grains",
                "Vegetables",
                "Fruits",
                "Dairy",
                "Meats",
                "Fats",
                "Drinks",
                "Sauces",
                "Others"
            )
        for (name in nameList) {
            val chip = createChip(name)
            testGroupChip.addView(chip)
        }
    }

    private fun createChip(name: String): Chip {
        val chip = ChipGroup.inflate(this, R.layout.choice_chip, null)
        (chip as Chip).text = name
        return chip
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerAdapter { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, m: Int, year: Int) {
        val month = m + 1
        etDate.setText("$day/$month/$year")
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerAdapter { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "time")

    }

    private fun onTimeSelected(time: String) {
        etTime.setText(time)
    }


}

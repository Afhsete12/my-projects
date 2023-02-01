package com.example.vlaka

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.children
import com.example.vlaka.adapters.TimePickerAdapter
import com.example.vlaka.adapters.DatePickerAdapter
import com.example.vlaka.data.models.Product
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import pl.droidsonroids.gif.GifImageView
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class LarderFormActivity : AppCompatActivity() {
    private var filePath: Uri? = null
    private lateinit var bitMapImg: Bitmap
    private lateinit var auth: FirebaseAuth
    private val PICK_IMAGE_REQUEST: Int = 20
    private lateinit var imageUrl: String
    private val db = FirebaseFirestore.getInstance()
    private lateinit var userDocRef: DocumentReference
    private lateinit var testGroupChip: ChipGroup
    private lateinit var textInputTime: TextInputLayout
    private var typeOfFood: String? = null
    private var operations: Int = 0
    private lateinit var etTime: EditText
    private lateinit var etDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larder_form)
        auth = Firebase.auth

        val pName = findViewById<TextInputEditText>(R.id.pName)
        val pCalories = findViewById<TextInputEditText>(R.id.pCalories)


        val btnSave = findViewById<Button>(R.id.btn_save)
        val test = findViewById<GifImageView>(R.id.gifSendInformation)
        val linearLayout = findViewById<LinearLayout>(R.id.LinLayoutForm)
        val stock = findViewById<TextView>(R.id.productStock)

        textInputTime = findViewById(R.id.textInputLayout)
        val swMaterial = findViewById<SwitchMaterial>(R.id.swMaterial)

        val increment = findViewById<ImageView>(R.id.incrementStock)
        val decrement = findViewById<ImageView>(R.id.decrementStock)

        val btnChooseImage = findViewById<MaterialButton>(R.id.selectImage)

        btnChooseImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
        }

        loadPickerTime()
        loadPickerDate()

        setupChip()

        swMaterial.setOnCheckedChangeListener { _, b ->
            if (b) {
                textInputTime.visibility = View.VISIBLE
            } else {
                textInputTime.visibility = View.INVISIBLE
            }
        }

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

        btnSave.setOnClickListener {

            val product = Product()

            product.name = pName.text.toString()
            getTypeOfFood()
            product.type = typeOfFood
            product.stock = stock.text.toString().toInt()
            product.time = etDate.text.toString()
            product.hour = etTime.text.toString()
            product.calories = pCalories.text.toString().toInt()

            test.visibility = View.VISIBLE
            linearLayout.visibility = View.GONE
            try {
                uploadToFirebase(bitMapImg, product)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            typeOfFood = ""
        }
    }

    private fun getTypeOfFood() {
        testGroupChip.children.forEach {
            if ((it as Chip).isChecked) {
                typeOfFood = it.text.toString()
            }
        }
        if (typeOfFood == null) {
            typeOfFood = "Others"
        }
    }

    private fun loadPickerDate() {
        etDate = findViewById(R.id.pDate)
        etDate.setOnClickListener { showDatePickerDialog() }
    }

    private fun loadPickerTime() {
        etTime = findViewById(R.id.pTime)
        etTime.setOnClickListener { showTimePickerDialog() }
    }

    private fun uploadToFirebase(bitmap: Bitmap, product: Product) {
        val storageRef = FirebaseStorage.getInstance().reference
        val name = UUID.randomUUID().toString()
        val mountainsRef = storageRef.child("imgProducts/$name.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.v("MyApp", it.message.toString())
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                Log.i("HomeFragment", "Todo ok $imageUrl")
                userDocRef = db.collection("pantries").document(auth.currentUser!!.uid)
                val keyProduct = userDocRef.collection("products").document().id

                Handler(Looper.getMainLooper()).postDelayed({

                    product.apply {
                        id = keyProduct
                        img = imageUrl
                    }

                    val intent = Intent(this, HomeActivity::class.java).apply {
                        putExtra("newProduct", product)
                    }
                    startActivity(intent)


                }, 1800)


            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                bitMapImg =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, filePath)
                val ivUser = findViewById<ImageView>(R.id.imgProduct)
                ivUser?.setImageBitmap(bitMapImg)
                //uploadToFirebase(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
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
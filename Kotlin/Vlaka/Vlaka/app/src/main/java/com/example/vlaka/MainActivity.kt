package com.example.vlaka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            db.collection("test").document("xc").set(
                hashMapOf(
                    "type" to "Hhdhdh",
                    "calories" to 123,
                    "dd" to 32,
                    "date" to "4",
                    "time" to "3:20"
                )
            ).addOnSuccessListener {
                Log.v("MyApp","Ok")
            }
        }*/
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHost.navController

    }
}
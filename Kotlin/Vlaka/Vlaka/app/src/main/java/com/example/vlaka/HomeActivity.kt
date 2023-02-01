package com.example.vlaka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vlaka.adapters.HomeAdapter
import com.example.vlaka.data.models.InfoAboutCalories
import com.example.vlaka.data.models.Product
import com.example.vlaka.views.SplashFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private var myProducts: ArrayList<Product> = arrayListOf()
    private lateinit var userDocRef: DocumentReference
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var noItemFound: LinearLayout
    private lateinit var mAdapter: HomeAdapter

    private lateinit var googleSignInClient: GoogleSignInClient

    private val totalCalories: ArrayList<InfoAboutCalories> = arrayListOf()

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        auth = Firebase.auth
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar_top))
        mainRecyclerView = findViewById(R.id.test2)
        noItemFound = findViewById(R.id.lLayoutNotFound)


        val toolbar = findViewById<BottomAppBar>(R.id.bottomAppBar)


        initGoogle()

        toolbar.setNavigationOnClickListener {
            Firebase.auth.signOut()
            googleSignInClient.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            var amountProducts = 0
            val categories: ArrayList<String> = arrayListOf()

            when (menuItem.itemId) {
                R.id.about -> {

                    userDocRef = db.collection("pantries").document(auth.currentUser!!.uid)
                    userDocRef.collection("products").get().addOnSuccessListener { result ->
                        for (document in result) {
                            val myEvent = document.toObject(Product::class.java)
                            amountProducts++
                            myEvent.type?.let { categories.add(it) }

                            val infoAboutCalories = InfoAboutCalories()
                            infoAboutCalories.pStock = myEvent.stock
                            infoAboutCalories.pCalories = myEvent.calories

                            totalCalories.add(infoAboutCalories)
                        }
                        val items = arrayOf(
                            "Number of products: $amountProducts",
                            "Categories: ${categories.toSet().toList().joinToString(",")}",
                            "Total calories: ${totalCalories.sumOf { it.pStock * it.pCalories }}  Kcal.",
                        )
                        MaterialAlertDialogBuilder(this)
                            .setTitle("General Info")
                            .setItems(items) { _, _ ->
                            }
                            .show()

                    }
                        .addOnFailureListener {
                        }

                    true
                }

                else -> false
            }
        }


        val layoutLoading = findViewById<LinearLayout>(R.id.layoutLoading)
        Handler(Looper.getMainLooper()).postDelayed({
            layoutLoading.isVisible = false
            mainRecyclerView.isVisible = true
        }, 2000)


        val btnAddProduct = findViewById<FloatingActionButton>(R.id.addProduct)

        btnAddProduct.setOnClickListener {
            startActivity(Intent(this, LarderFormActivity::class.java))
        }
        val bundle: Bundle? = intent?.extras
        val myUser: Product? = bundle?.getSerializable("newProduct") as Product?

        myUser?.let {
            userDocRef = db.collection("pantries").document(auth.currentUser!!.uid)
            userDocRef.collection("products")
                .document(it.id).set(it).addOnSuccessListener {
                }
        }

        getMyEvents()

        mAdapter = HomeAdapter(myProducts)

        mainRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainRecyclerView.adapter = mAdapter


    }

    private fun initGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.svNames -> {
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        searchView.clearFocus()
                        return false
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        filterByName(query)
                        return true
                    }

                    private fun filterByName(query: String?) {
                        val filteredList: ArrayList<Product> = arrayListOf()
                        if (query != null) {
                            val listUpdated = updateList(myProducts, query, filteredList)
                            if (listUpdated.size == 0) {
                                mainRecyclerView.visibility = View.GONE
                                noItemFound.visibility = View.VISIBLE
                            } else {
                                noItemFound.visibility = View.GONE
                                mainRecyclerView.visibility = View.VISIBLE
                                mAdapter.filteredList(listUpdated)
                            }
                        }
                    }

                })
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateList(
        currentlyList: ArrayList<Product>,
        query: String,
        newListUpdated: ArrayList<Product>
    ): ArrayList<Product> {
        currentlyList.forEach {
            if (it.name.lowercase().startsWith(query.lowercase())) {
                newListUpdated.add(it)
            }
        }
        return newListUpdated
    }


    private fun getMyEvents() {
        userDocRef = db.collection("pantries").document(auth.currentUser!!.uid)
        userDocRef.collection("products").get().addOnSuccessListener { result ->
            myProducts.clear()
            for (document in result) {
                val myEvent = document.toObject(Product::class.java)
                myProducts.add(myEvent)
            }
            /*if (myEvents.size == 0) {
                hidden.visibility = View.VISIBLE
            } else {
                mainRecyclerView.visibility = View.VISIBLE
            }*/
            mAdapter.notifyDataSetChanged()
        }
            .addOnFailureListener {
            }

    }

}


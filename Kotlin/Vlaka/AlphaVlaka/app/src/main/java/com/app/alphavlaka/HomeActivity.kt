package com.app.alphavlaka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.alphavlaka.adapters.MainAdapter
import com.app.alphavlaka.data.models.Product
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeActivity : AppCompatActivity() {
    private lateinit var noItemFound: LinearLayout
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var mAdapter: MainAdapter

    var products: ArrayList<Product> = arrayListOf(
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Pera",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Tomate",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://images.unsplash.com/photo-1550321989-65d089904d5c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        ),
        Product(
            "1784b21c-04f7",
            "https://cdn.pixabay.com/photo/2019/11/04/07/48/pumpkins-4600419_960_720.jpg",
            "Alcachofa",
            "Food",
            4,
            "10/5/20",
            "10:40",
            4000f
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        title = ""
        setSupportActionBar(findViewById(R.id.toolbar_top))
        mainRecyclerView = findViewById(R.id.test2)
        val layoutLoading = findViewById<LinearLayout>(R.id.layoutLoading)
        Handler(Looper.getMainLooper()).postDelayed({
            layoutLoading.isVisible = false
            mainRecyclerView.isVisible = true
        }, 2000)
        noItemFound = findViewById(R.id.lLayoutNotFound)
        mAdapter = MainAdapter(products)
        mainRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainRecyclerView.adapter = mAdapter

        val toolbar = findViewById<BottomAppBar>(R.id.bottomAppBar)


        toolbar.setNavigationOnClickListener {

        }
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about -> {
                    // Handle search icon press
                    val items = arrayOf(
                        "Number of products: ",
                        "Categories:",
                        "Total calories:  Kcal.",
                        "Expired products:"
                    )
                    MaterialAlertDialogBuilder(this)
                        .setTitle("General Info")
                        .setItems(items) { _, _ ->
                        }
                        .show()
                    true
                }
                /*R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    true
                }*/
                else -> false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
                        if (query != null && query.length >= 2) {
                            val listUpdated = updateList(products, query, filteredList)
                            if (listUpdated.isNotEmpty()) {
                                noItemFound.visibility = View.GONE
                                mainRecyclerView.visibility = View.VISIBLE
                                //mAdapter = MainAdapter(listUpdated)
                                mAdapter.filteredList(listUpdated)
                                /*mainRecyclerView.layoutManager =
                                    LinearLayoutManager(
                                        baseContext,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )*/
                                mainRecyclerView.adapter = mAdapter
                            }else{
                                mainRecyclerView.visibility = View.GONE
                                noItemFound.visibility = View.VISIBLE
                            }
                        }else{
                            mAdapter = MainAdapter(products)
                            mainRecyclerView.adapter = mAdapter
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
}
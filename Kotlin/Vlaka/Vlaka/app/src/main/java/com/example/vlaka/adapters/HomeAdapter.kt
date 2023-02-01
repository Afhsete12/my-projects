package com.example.vlaka.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vlaka.R
import com.example.vlaka.data.models.Product
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class HomeAdapter(private var mDataSet: ArrayList<Product>) :
    RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {
    private var operations: Int = 0
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_block, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = mDataSet[position]
        holder.bindItems(data)
        holder.getItemPosition(data.id, holder.bindingAdapterPosition)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    inner class MainViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: Product) {
            auth = Firebase.auth
            val stock = view.findViewById<TextView>(R.id.units)
            view.findViewById<TextView>(R.id.productName).text = data.name
            Picasso.get().load(data.img).into(view.findViewById<ShapeableImageView>(R.id.icon))
            view.findViewById<TextView>(R.id.categoryProduct).text = data.type
            stock.text = data.stock.toString()
            view.findViewById<TextView>(R.id.dateProduct).text = data.time

            if (data.hour.isNullOrEmpty()){
                view.findViewById<TextView>(R.id.timeProduct).visibility = View.GONE
            }else{
                view.findViewById<TextView>(R.id.timeProduct).text = data.hour
            }

            view.findViewById<TextView>(R.id.calories).text = data.calories.toString()

            val increment = view.findViewById<ImageView>(R.id.incrementStock)
            val decrement = view.findViewById<ImageView>(R.id.decrementStock)

            val updateStock = view.findViewById<Button>(R.id.updateStock)

            val cardView = view.findViewById<MaterialCardView>(R.id.base_cardview)
            val arrow = view.findViewById<ImageView>(R.id.arrow_button)
            val hiddenView = view.findViewById<LinearLayout>(R.id.hidden_view)


            arrow.setOnClickListener {
                if (hiddenView.visibility == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(
                        cardView,
                        AutoTransition()
                    )
                    hiddenView.visibility = View.GONE
                    increment.visibility = View.GONE
                    decrement.visibility = View.GONE
                    arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
                } else {
                    TransitionManager.beginDelayedTransition(
                        cardView,
                        AutoTransition()
                    )
                    hiddenView.visibility = View.VISIBLE
                    arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
                }
            }


            updateStock.setOnClickListener {
                increment.visibility = View.VISIBLE
                decrement.visibility = View.VISIBLE
            }


            increment.setOnClickListener {
                operations = stock.text.toString().toInt() + 1
                stock.text = operations.toString()
                /*db.collection("testProduct").document(data.id)
                    .update("stock", stock.text.toString().toInt())*/
                val userDocRef = db.collection("pantries").document(auth.currentUser!!.uid)
                userDocRef.collection("products").document(data.id)
                    .update("stock", stock.text.toString().toInt())
            }
            decrement.setOnClickListener {
                if (stock.text.toString().toInt() != 1) {
                    operations = stock.text.toString().toInt() - 1
                    stock.text = operations.toString()
                    /*db.collection("testProduct").document(data.id)
                        .update("stock", stock.text.toString().toInt())*/
                    val userDocRef = db.collection("pantries").document(auth.currentUser!!.uid)
                    userDocRef.collection("products").document(data.id)
                        .update("stock", stock.text.toString().toInt())
                }
            }


        }

        fun getItemPosition(uid: String, id: Int) {
            val removeItem = view.findViewById<Button>(R.id.removeItem)
            removeItem.setOnClickListener {
                MaterialAlertDialogBuilder(view.context)
                    .setTitle("Are you sure you want to delete this product?")
                    .setNegativeButton("Cancel") { _, _ -> }
                    .setPositiveButton("Accept") { _, _ ->
                        mDataSet.removeAt(id)
                        notifyItemRemoved(id)
                        notifyItemRangeChanged(id, mDataSet.size)
                        val userDocRef = db.collection("pantries").document(auth.currentUser!!.uid)
                        userDocRef.collection("products").document(uid).delete()
                    }
                    .show()
            }
        }
    }


    fun filteredList(list: ArrayList<Product>) {
        mDataSet = list
        notifyDataSetChanged()
    }
}
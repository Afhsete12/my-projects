package com.app.alphavlaka.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.alphavlaka.data.models.Product
import com.app.alphavlaka.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class MainAdapter(private var mDataSet: ArrayList<Product>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_block, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = mDataSet[position]
        holder.bindItems(data)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    inner class MainViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: Product) {
            view.findViewById<TextView>(R.id.productName).text = data.name
            view.findViewById<TextView>(R.id.categoryProduct).text = data.type
            view.findViewById<TextView>(R.id.units).text = data.stock.toString()
            view.findViewById<TextView>(R.id.dateAndTime).text = "${data.time}  ${data.hour}"
            view.findViewById<TextView>(R.id.calories).text = data.calories.toString()

            val cardView = view.findViewById<MaterialCardView>(R.id.base_cardview)
            val arrow = view.findViewById<ImageView>(R.id.arrow_button)
            val hiddenView = view.findViewById<LinearLayout>(R.id.hidden_view)

            //val image = view.findViewById<ShapeableImageView>(R.id.icon)

            Picasso.get().load(data.img).into(view.findViewById<ShapeableImageView>(R.id.icon))

            arrow.setOnClickListener {
                if (hiddenView.visibility == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(
                        cardView,
                        AutoTransition()
                    )
                    hiddenView.visibility = View.GONE
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

        }
    }

    fun filteredList(list: ArrayList<Product>) {
        mDataSet = list
        notifyDataSetChanged()
    }
}
package com.ozturksahinyetisir.travelguideapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.databinding.RecyclerItemMightBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.view.DetailActivity
import com.squareup.picasso.Picasso

class MightAdapter(val context: Context, val travelModel: List<TravelModel>) :
    RecyclerView.Adapter<MightAdapter.TravelMightModelViewHolder>() {
    val TAG = "MightAdapter"

    /**
     * set recycler layouts onCreateViewHolder and ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelMightModelViewHolder {

        return TravelMightModelViewHolder(
            RecyclerItemMightBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TravelMightModelViewHolder, position: Int) {
        /**
         * get travelModel position and set into Picasso for getting image.
         */
        val item = travelModel[position]

        holder.budgetTv.text = item.title


        Picasso.get()
            .load(item.images?.get(0)?.url)
            .placeholder(R.drawable.ic_loader)
            .error(R.drawable.ic_fill_home)
            .into(holder.mightTopImage)
        /**
         * [setOnClickListener] on itemView new intent to [DetailActivity]
         * and sends data with [intent.putExtra].
         */
        holder.itemView.setOnClickListener {
            Log.e(TAG, "click ${item.id}")
            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra("id", item.id.toString())
            intent.putExtra("imgurl", item.images?.get(0)?.url)
            intent.putExtra("title", item.title)
            intent.putExtra("description", item.description)
            intent.putExtra("country", item.country)
            intent.putExtra("category", item.category)
            intent.putExtra("bookmark",item.isBookmark)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return travelModel.count()
    }
    /**
     * bind RecyclerItems to use in onBindViewHolder
     */
    inner class TravelMightModelViewHolder(recyclerItemMightBinding: RecyclerItemMightBinding) :
        RecyclerView.ViewHolder(recyclerItemMightBinding.root) {

        val mightTopImage = recyclerItemMightBinding.mightTopImage
        val budgetTv = recyclerItemMightBinding.budgetTv
    }

}


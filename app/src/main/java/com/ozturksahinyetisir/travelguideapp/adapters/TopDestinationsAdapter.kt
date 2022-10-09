package com.ozturksahinyetisir.travelguideapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.databinding.RecyclerItem2Binding
import com.ozturksahinyetisir.travelguideapp.domain.model.*
import com.ozturksahinyetisir.travelguideapp.view.DetailActivity
import com.squareup.picasso.Picasso

class TopDestinationsAdapter(val context: Context, val travelModel: List<TravelModel>) :
    RecyclerView.Adapter<TopDestinationsAdapter.TravelMightModelViewHolder>() {
    val TAG = "TopDestinationAdapter"
    /**
     * set recycler layouts onCreateViewHolder and ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelMightModelViewHolder {
        return TravelMightModelViewHolder(RecyclerItem2Binding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun onBindViewHolder(holder: TravelMightModelViewHolder, position: Int) {
        /**
         * get travelModel position and set into Picasso for getting image.
         */
        val item = travelModel[position]

        holder.city.text = item.city
        holder.country.text = item.country

       Picasso.get()
            .load(item.images?.get(0)?.url)
            .placeholder(R.drawable.ic_loader)
            .error(R.drawable.ic_fill_star)
            .into(holder.mightTopImage)

        /**
         * [setOnClickListener] on itemView new intent to [DetailActivity]
         * and sends data with [intent.putExtra].
         */
        holder.itemView.setOnClickListener {
            Log.e(TAG, "click ${item.id}")
            val intent = Intent(context, DetailActivity::class.java)


            intent.putExtra("id",item.id.toString())
            intent.putExtra("imgurl",item.images?.get(0)?.url)
            intent.putExtra("title",item.title)
            intent.putExtra("description",item.description)
            intent.putExtra("country",item.country)
            intent.putExtra("category",item.category)
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
    inner class TravelMightModelViewHolder(recyclerItem2Binding: RecyclerItem2Binding) :
        RecyclerView.ViewHolder(recyclerItem2Binding.root) {


        val mightTopImage = recyclerItem2Binding.mightTopImage
        val city = recyclerItem2Binding.cityTv
        val country = recyclerItem2Binding.countryTv
    }
}
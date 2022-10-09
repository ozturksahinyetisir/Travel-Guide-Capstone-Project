package com.ozturksahinyetisir.travelguideapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.databinding.RecyclerTripBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.view.DetailActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class TripAdapter(val context: Context, var travelModel: List<TravelModel>) :
    RecyclerView.Adapter<TripAdapter.TravelRoomModelViewHolder>() {
    val TAG = "NearbyAdapter"

    /**
     * set recycler layouts onCreateViewHolder and ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelRoomModelViewHolder {

        return TravelRoomModelViewHolder(
            RecyclerTripBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TravelRoomModelViewHolder, position: Int) {
        /**
         * get travelModel position and set into Picasso for getting image.
         */
        val item = travelModel[position]
        val imgitem = travelModel[position].images?.get(0)?.url
        var preferences = context.getSharedPreferences("tripdata",Context.MODE_PRIVATE)
        var prefDate = preferences.getString("date","")
        //TODO: SimpleDateFormat takes also hours,minutes & seconds too, remove that unnecessary parts.To calculate between 2 date diff.
        val sdf = SimpleDateFormat("dd/MM/yyyy")


        Picasso.get()
            .load(imgitem)
            .placeholder(R.drawable.ic_loader)
            .into(holder.centerImage)

        holder.centerName.text = item.title
        holder.centerDate.text = prefDate.toString()

        /**
         * [setOnClickListener] on [itemView] new intent to [DetailActivity]
         * and sends data with [intent.putExtra].
         */

        holder.itemView.setOnClickListener {
            Log.e(TAG, "click ${item.id}")
            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra("id", item.id.toString())
            intent.putExtra("title", item.title)
            intent.putExtra("imgurl", item.images?.get(0)?.url)
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
    inner class TravelRoomModelViewHolder(recyclerTripBinding: RecyclerTripBinding) :
        RecyclerView.ViewHolder(recyclerTripBinding.root) {

        val centerImage = recyclerTripBinding.nearbyImage
        val centerName = recyclerTripBinding.centerTv1
        val centerDate = recyclerTripBinding.centerTv2
    }
}
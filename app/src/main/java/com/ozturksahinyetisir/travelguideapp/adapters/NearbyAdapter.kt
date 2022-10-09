package com.ozturksahinyetisir.travelguideapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.databinding.RecyclerItemSearchWideBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase
import com.ozturksahinyetisir.travelguideapp.view.DetailActivity
import com.squareup.picasso.Picasso

class NearbyAdapter(val context: Context, var travelModel: List<TravelModel>) :
    RecyclerView.Adapter<NearbyAdapter.TravelNearbyModelViewHolder>() {
    val TAG = "NearbyAdapter"

    /**
     * set recycler layouts onCreateViewHolder and ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelNearbyModelViewHolder {
        return TravelNearbyModelViewHolder(
            RecyclerItemSearchWideBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TravelNearbyModelViewHolder, position: Int) {
        /**
         * get travelModel position and set into Picasso for getting image.
         */
        val item = travelModel[position]

        holder.nearbyHotel.text = item.title
        holder.nearbyLocation.text = item.country
        holder.lefTopCategory.text = item.category?.uppercase()





        Picasso.get()
            .load(item.images?.get(0)?.url)
            .placeholder(R.drawable.ic_loader)
            .error(R.drawable.ic_loader)
            .into(holder.nearbyImage)

        /**
         * [setOnClickListener] on [itemView] new intent to [DetailActivity]
         * and sends data with [intent.putExtra].
         * [setOnClickListener] on [bookmark] gives error log to get [item.id].
         */

        if (item.isBookmark) Picasso.get().load(R.drawable.icon_bookmark_red).into(holder.bookmark)

        holder.bookmark.setOnClickListener {
            val data = TravelDatabase.getDatabase(context)

            if (item.isBookmark == true) {
                Log.e(TAG, "click true ${item.id} ${item.isBookmark}")
                item.isBookmark = false
                data.roomDao().updateBookmark(item.id, false)
                Picasso.get().load(R.drawable.icon_bookmark).into(holder.bookmark)

            } else {
                item.isBookmark = true
                Log.e(TAG, "click false ${item.id}  ${item.isBookmark}")
                data.roomDao().updateBookmark(item.id, true)
                Picasso.get().load(R.drawable.icon_bookmark_red).into(holder.bookmark)
            }


        }
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
    inner class TravelNearbyModelViewHolder(recyclerItemSearchWideBinding: RecyclerItemSearchWideBinding) :
        RecyclerView.ViewHolder(recyclerItemSearchWideBinding.root) {

        val nearbyImage = recyclerItemSearchWideBinding.nearbyImage
        val nearbyHotel = recyclerItemSearchWideBinding.centerTv1
        val nearbyLocation = recyclerItemSearchWideBinding.centerTv2
        val lefTopCategory = recyclerItemSearchWideBinding.leftTopHotelTv
        val bookmark = recyclerItemSearchWideBinding.bookmarkImage

    }
}



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

class BookmarkAdapter(val context: Context, var travelModel: List<TravelModel>) :
    RecyclerView.Adapter<BookmarkAdapter.TravelRoomModelViewHolder>() {
    val TAG = "NearbyAdapter"

    /**
     * set recycler layouts onCreateViewHolder and ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelRoomModelViewHolder {
        return TravelRoomModelViewHolder(
            RecyclerItemSearchWideBinding.inflate(
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

        if (item.isBookmark) Picasso.get().load(R.drawable.icon_bookmark_red)
            .into(holder.bookmarkImage)

        Picasso.get()
            .load(imgitem)
            .placeholder(R.drawable.ic_loader)
            .into(holder.nearbyImage)

        holder.nearbyHotel.text = item.title
        holder.nearbyLocation.text = item.country
        holder.lefTopCategory.text = item.category?.uppercase()

        /**
         * [setOnClickListener] on [itemView] new intent to [DetailActivity]
         * and sends data with [intent.putExtra].
         * [setOnClickListener] on [bookmark] gives error log to get [item.id].
         */
        holder.bookmarkImage.setOnClickListener {
            Log.e(TAG, "click bookmark ${item.id}")
            val data = TravelDatabase.getDatabase(context)
            data.roomDao().updateBookmark(item.id, false)
            Picasso.get().load(R.drawable.ic_bookmark).into(holder.bookmarkImage)


        }
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
    inner class TravelRoomModelViewHolder(recyclerItemSearchWideBinding: RecyclerItemSearchWideBinding) :
        RecyclerView.ViewHolder(recyclerItemSearchWideBinding.root) {

        val nearbyImage = recyclerItemSearchWideBinding.nearbyImage
        val nearbyHotel = recyclerItemSearchWideBinding.centerTv1
        val nearbyLocation = recyclerItemSearchWideBinding.centerTv2
        val lefTopCategory = recyclerItemSearchWideBinding.leftTopHotelTv
        val bookmarkImage = recyclerItemSearchWideBinding.bookmarkImage

    }
}



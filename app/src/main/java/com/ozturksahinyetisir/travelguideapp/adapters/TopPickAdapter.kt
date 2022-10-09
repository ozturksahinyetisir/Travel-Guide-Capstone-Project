package com.ozturksahinyetisir.travelguideapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.databinding.RecyclerItemTopPickBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.view.DetailActivity
import com.squareup.picasso.Picasso

class TopPickAdapter(val context: Context, val travelModel: List<TravelModel>) :
    RecyclerView.Adapter<TopPickAdapter.TravelTopPickModelViewHolder>() {
    val TAG = "TopPickAdapter"

    /**
     * set recycler layouts onCreateViewHolder and ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelTopPickModelViewHolder {
        return TravelTopPickModelViewHolder(RecyclerItemTopPickBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TravelTopPickModelViewHolder, position: Int) {
        /**
         * get travelModel position and set into Picasso for getting image.
         */
        val item = travelModel[position]

        holder.title.text = item.title
        holder.info.text = item.description

        Picasso.get()
            .load(item.images?.get(0)?.url)
            .placeholder(R.drawable.ic_loader)
            .into(holder.topBlogImage)


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
    inner class TravelTopPickModelViewHolder(recyclerItemTopPickBinding: RecyclerItemTopPickBinding) :
        RecyclerView.ViewHolder(recyclerItemTopPickBinding.root) {


        val topBlogImage = recyclerItemTopPickBinding.topBlogImage
        val title = recyclerItemTopPickBinding.categoryTv
        val info = recyclerItemTopPickBinding.informationTv

    }

}
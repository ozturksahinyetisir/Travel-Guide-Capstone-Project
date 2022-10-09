package com.ozturksahinyetisir.travelguideapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.databinding.RecyclerItemBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.view.DetailActivity
import com.squareup.picasso.Picasso

class RecyclerAdapter(val context: Context, val travelModel: List<TravelModel>) :
    RecyclerView.Adapter<RecyclerAdapter.TravelModelViewHolder>(){
    val TAG = "RecyclerAdapter"
    /**
     * set recycler layouts onCreateViewHolder and ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelModelViewHolder {
        return TravelModelViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun onBindViewHolder(holder: TravelModelViewHolder, position: Int) {
        /**
         * get travelModel position and set into Picasso for getting image.
         */
        val item = travelModel[position]

        holder.category.text = item.category
        holder.hotelname.text = item.title

       Picasso.get()
            .load(item.images?.get(0)?.url)
            .placeholder(R.drawable.ic_loader)
            .into(holder.travelImage)


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
    inner class TravelModelViewHolder(recyclerItemBinding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(recyclerItemBinding.root) {

        val travelImage = recyclerItemBinding.travelImage
        val category = recyclerItemBinding.homeRecycTitle
        val hotelname = recyclerItemBinding.homeRecycInfo

    }
}
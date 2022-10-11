package com.ozturksahinyetisir.travelguideapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.databinding.RecyclerDetailBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.view.DetailActivity
import com.squareup.picasso.Picasso

class DetailAdapter(val context: Context, val imgList: List<TravelModel.ImageRoomlist>) :
    RecyclerView.Adapter<DetailAdapter.TravelDetailModelViewHolder>() {
    val TAG = "DetailAdapter"

    /**
     * [clickListener] get img_src string on item click
     * and send this to [DetailActivity]
     */
    interface clickListener {
        fun imgClick(img_src: String)
    }

    lateinit var mListener: clickListener


    fun ClickListener(listener: clickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelDetailModelViewHolder {
        val travelBinding = RecyclerDetailBinding.inflate(LayoutInflater.from(parent.context))

        return TravelDetailModelViewHolder(travelBinding,mListener)
    }

    override fun onBindViewHolder(holder: TravelDetailModelViewHolder, position: Int) {
        /**
         * get travelModel second image.
         */
        val imgitem = imgList[position].url

        Picasso.get()
            .load(imgitem)
            .placeholder(R.drawable.ic_loader)
            .into(holder.detailSmallImage)

        /**
         * [setOnClickListener] on itemView new intent to [DetailActivity]
         * and sends data with [intent.putExtra].
         */

    }

    override fun getItemCount(): Int {
        return imgList.count()
    }
    /**
     * bind RecyclerItems to use in onBindViewHolder
     * [itemView.setOnClickListener] send images index 1 and set this item on topside of [DetailActivity].
     */
    inner class TravelDetailModelViewHolder(recyclerDetailBinding: RecyclerDetailBinding,listener: clickListener) :
        RecyclerView.ViewHolder(recyclerDetailBinding.root) {

        val detailSmallImage = recyclerDetailBinding.detailSmallImage
        init {
            itemView.setOnClickListener {
                imgList[absoluteAdapterPosition].url.let { it2->listener.imgClick(it2!!) }
            }
        }
    }
}



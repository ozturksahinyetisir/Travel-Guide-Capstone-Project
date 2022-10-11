package com.ozturksahinyetisir.travelguideapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.adapters.DetailAdapter
import com.ozturksahinyetisir.travelguideapp.databinding.ActivityDetailBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    val TAG = "DetailActivity"

    private lateinit var binding: ActivityDetailBinding
    private var detailAdapter: DetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        /**
         * [intent.getStringExtra] gets data from another fragment or adapter.
         * Picasso load img into [topBg]
         */
        val id = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("description")
        val country = intent.getStringExtra("country")
        val imgurl = intent.getStringExtra("imgurl")
        /**
         * text set with dataBinding & strings get from Adapters.
         */
        Picasso.get().load(imgurl).into(binding.topBg)
        binding.topTv.text = "$title"
        binding.descriptionTv.text = "$desc"
        binding.locationTv.text = "$country"

        binding.arrow1.setOnClickListener {

            if (chosenImg != "") {
                FullscreenImageDialog(this, chosenImg).show()

            }


        }

        val data = TravelDatabase.getDatabase(this@DetailActivity)

        /**
         * [forEach] returns all travelModel and if id matchs with img_click where to send from Adapter to DetailActivity.
         * Set this match travelModel.images for detail of Main Image
         */
        data.roomDao().getTravel().forEach { travelModel ->
            travelModel.isBookmark
            Log.e(TAG, "click ${travelModel.id}")

            if (travelModel.id.toString() == id) {
                travelModel?.let {
                    setupRecy(travelModel.images!!)
                }
            }
        }
        /**
         * if Bookmark is true set new list as newList. Update bookmark icon & boolean value with setOnClickListener
         */
        data.roomDao().getTravel().forEach { newList ->
            if (newList.id.toString() == id) {
                binding.bookmarkButton.setOnClickListener {
                    val data = TravelDatabase.getDatabase(this@DetailActivity)
                    if (newList.isBookmark == true) {
                        Log.e(TAG, "click true ${newList.id} ${newList.isBookmark}")
                        newList.isBookmark = false
                        data.roomDao().updateBookmark(newList.id, false)
                        binding.bookmarkTv.text = "Add Bookmark"

                    } else {
                        newList.isBookmark = true
                        Log.e(TAG, "click false ${newList.id}  ${newList.isBookmark}")
                        data.roomDao().updateBookmark(newList.id, true)
                        binding.bookmarkTv.text = "Remove Bookmark"

                    }
                }
            }
        }
        setContentView(binding.root)

    }

    /**
     * [setupRecy] setup adapter to RecyclerView.
     */
    var chosenImg = ""
    fun setupRecy(newList: List<TravelModel.ImageRoomlist>) {
        binding.gridsDetail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        detailAdapter = DetailAdapter(this@DetailActivity, newList)
        binding.gridsDetail.adapter = detailAdapter

        /**
         * [imgClick] change top image with detail images.
         */

        chosenImg = newList[0].url.toString()
        detailAdapter!!.ClickListener(object : DetailAdapter.clickListener {
            override fun imgClick(img_src: String) {
                chosenImg = img_src
                Picasso.get()
                    .load(img_src)
                    .placeholder(R.drawable.ic_loader)
                    .into(binding.topBg)
            }
        })
    }
}




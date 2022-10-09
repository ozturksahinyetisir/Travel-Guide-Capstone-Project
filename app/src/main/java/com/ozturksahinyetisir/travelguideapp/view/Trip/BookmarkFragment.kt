package com.ozturksahinyetisir.travelguideapp.view.Trip

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.adapters.BookmarkAdapter
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentBookmarkBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase

class BookmarkFragment : Fragment() {
    private var bookmarkAdapter: BookmarkAdapter? = null
    private lateinit var binding: FragmentBookmarkBinding
    val TAG = "BookmarkFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(layoutInflater)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { activity ->

            try {
                val data = TravelDatabase.getDatabase(activity)
                val list = data.roomDao().getTravel()

                val bookmarkList = list.filter {
                    it.isBookmark == true

                }
                setupRecyclerViewBookmark(bookmarkList as ArrayList<TravelModel>)
            } catch (e: Exception) {
                Log.e(TAG, "onViewCreated: ex ${e.message}")
            }


            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(
                activity.applicationContext,
                1,
                GridLayoutManager.VERTICAL,
                false
            )
            binding.grids.layoutManager = layoutManager
        }

    }

    /**
     * [setupRecyclerViewBookmark] -> [newList] as TravelModel
     * using dataBinding set adapter [nearbyAdapter]
     */
    private fun setupRecyclerViewBookmark(newList: ArrayList<TravelModel>) {

        activity?.let { activity ->
            bookmarkAdapter = BookmarkAdapter(activity, newList)
            binding.grids.adapter = bookmarkAdapter
            binding.grids.setHasFixedSize(true)

        }

    }

}
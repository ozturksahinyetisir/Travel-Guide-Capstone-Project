package com.ozturksahinyetisir.travelguideapp.view.Trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.adapters.ViewPagerAdapter
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentTripBinding
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase


class TripFragment : Fragment() {
    private lateinit var binding: FragmentTripBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTripBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * [childFragmentManager] does whole function. After changing fragment
     * view pager doesn't load with supportFragmentManager.
     * [addFraggment] creates new tabLayout with name it as "Trips", "Bookmark".
     */

    override fun onResume() {
        super.onResume()
        activity?.let {activity->
            val adapter = ViewPagerAdapter(childFragmentManager)
            adapter.addFragment(TripListFragment(), "Trips")
            adapter.addFragment(BookmarkFragment(), "Bookmark")

            binding.viewPager.adapter = adapter

            binding.tabLayout.setupWithViewPager(binding.viewPager)
            binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_trip)
            binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_fill_bookmark)


            binding.cleanButton.setOnClickListener{
                Toast.makeText(context, "All bookmarks removed.", Toast.LENGTH_SHORT).show()
                val data = TravelDatabase.getDatabase(activity)
                data.roomDao().getTravel().forEach { travelModel ->
                    data.roomDao().updateBookmark(travelModel.id, false)

                }
            }


        }
    }
}

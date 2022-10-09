package com.ozturksahinyetisir.travelguideapp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ozturksahinyetisir.travelguideapp.adapters.ViewPagerAdapter
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    val TAG ="HomeFragment"

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):  View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        /**
         * [setOnClickListener] used for shows button as clickable and effective.
         * Can change if created new fragments.
         */
        binding.flightsIcon.setOnClickListener{
            Toast.makeText(activity, "clicked flightIcon", Toast.LENGTH_SHORT).show()
        }
        binding.hotelsIcon.setOnClickListener{
            Toast.makeText(activity, "clicked HotelIcon", Toast.LENGTH_SHORT).show()
        }
        binding.carIcon.setOnClickListener{
            Toast.makeText(activity, "clicked carIcon", Toast.LENGTH_SHORT).show()
        }
        binding.taxiIcon.setOnClickListener{
            Toast.makeText(activity, "clicked taxiIcon", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }
    /**
     * [childFragmentManager] does all effect at here. After changing fragment
     * view pager doesn't load with supportFragmentManager.
     * [addFraggment] creates new tabLayout with name it as "All", "Flights", "Hotels", "Transportations".
     */
    override fun onResume() {
        super.onResume()
        activity?.let {activity->

            val adapter = ViewPagerAdapter(childFragmentManager)
            adapter.addFragment(AllFragment(), "All")
            adapter.addFragment(FlightsFragment(), "Flights")
            adapter.addFragment(HotelsFragment(), "Hotels")
            adapter.addFragment(TransportationsFragment(), "Transportations")

            binding.viewPager.adapter = adapter
            binding.tabLayout.setupWithViewPager(binding.viewPager)
        }
    }
}

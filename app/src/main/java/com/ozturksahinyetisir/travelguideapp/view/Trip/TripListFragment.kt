package com.ozturksahinyetisir.travelguideapp.view.Trip

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.adapters.TripAdapter
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentTripListBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase
import java.util.*


class TripListFragment : Fragment() {
    val TAG = "TripListFragment"
    private var tripAdapter: TripAdapter? = null
    private lateinit var binding: FragmentTripListBinding
    private var travelModel: List<TravelModel>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripListBinding.inflate(layoutInflater)
        /**
         * open new trip dialog with [floatingActionButton]
         */
        binding.floatingActionButton.setOnClickListener {

            Log.e(TAG, "floating button clicked")
            activity?.let { activity ->
                val dialog = NewTripDialog(activity)
                dialog.setOnDismissListener {
                    getData(activity)
                }
                dialog.show()

            }


        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            /**
             * get data from room database
             */
            getData(activity)


        }

    }

    fun getData(activity: FragmentActivity) {
        try {
            val data = TravelDatabase.getDatabase(activity)
            travelModel = data.roomDao().getTravel()
            travelModel?.let { travelModel ->
                /**
                 * [customList2] filters our choice at dialog screen and save this trip for us.
                 * used sharedPreferences at here.
                 */
                val customList2 = travelModel.filter {
                    var preferences = requireActivity().applicationContext.getSharedPreferences(
                        "tripdata",
                        Context.MODE_PRIVATE
                    )
                    var trip = preferences.getString("trip", "")
                    Log.e(TAG, "trip ${trip}")
                    it.title == trip
                }
                setupRecyclerViewTrip(customList2 as ArrayList<TravelModel>)
            }
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


    /**
     * [setupRecyclerViewBookmark] -> [newList] as TravelModel
     * using dataBinding set adapter [nearbyAdapter]
     */
    private fun setupRecyclerViewTrip(newList: ArrayList<TravelModel>) {

        activity?.let { activity ->
            tripAdapter = TripAdapter(activity, newList)
            binding.grids.adapter = tripAdapter
            binding.grids.setHasFixedSize(true)

        }

    }
}












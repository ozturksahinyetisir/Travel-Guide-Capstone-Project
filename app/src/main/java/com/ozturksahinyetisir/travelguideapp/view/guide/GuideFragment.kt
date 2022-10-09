package com.ozturksahinyetisir.travelguideapp.view.guide

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ozturksahinyetisir.travelguideapp.adapters.MightAdapter
import com.ozturksahinyetisir.travelguideapp.adapters.TopPickAdapter
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentGuideBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase


class GuideFragment : Fragment() {
    val TAG = "GuideFragment"
    private var mightAdapter: MightAdapter? = null
    private var topPickAdapter: TopPickAdapter? = null
    private var travelModel: List<TravelModel>? = null
    private lateinit var binding: FragmentGuideBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuideBinding.inflate(layoutInflater)
        binding.sightseeing.setOnClickListener{
            Toast.makeText(context, "Sightseeing", Toast.LENGTH_SHORT).show()
        }
        binding.resort.setOnClickListener{
            Toast.makeText(context, "Resort", Toast.LENGTH_SHORT).show()
        }
        binding.restaurant.setOnClickListener{
            Toast.makeText(context, "Restaurant", Toast.LENGTH_SHORT).show()
        }
        binding.best.setOnClickListener{
            Toast.makeText(context, "Best", Toast.LENGTH_SHORT).show()
        }
        binding.editText1.doOnTextChanged { text, start, before, count ->

            val filterList = ArrayList<TravelModel>()
            val topPickList = travelModel?.filter {
                it.category == "toppick"
            }

            setupRecyclerViewTopPick(topPickList as ArrayList<TravelModel>)
            /**
             * @param forEach return all data, trim and match with title add this item  [filterList]
             * if size>0 setup [filterList] as ArrayList
             */

            topPickList?.forEach { item ->
                Log.e(TAG, "onCreateView: ${item.title} $text")

                if (item.title.toString().trim().contains(text.toString().trim())) {
                    filterList.add(item)
                    if (filterList.size > 0) {
                        setupRecyclerViewTopPick(filterList)
                    }
                }
            }

            if (text.toString() == "") {
                travelModel?.let { activity ->
                    val topPickList = travelModel!!.filter {
                        it.category == "toppick"
                    }
                    setupRecyclerViewTopPick(topPickList as ArrayList<TravelModel>)
                }
            }
        }

        return binding.root
    }

    /**
     * [getData] onViewCreated & bind data to RecyclerView vertical & horizontal.
     * binds it into [grids2] & [grids3].
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->

            try {
                val data = TravelDatabase.getDatabase(activity)
                travelModel = data.roomDao().getTravel()
                travelModel?.let { travelModel ->

                    val topPickList = travelModel.filter {
                        it.category == "toppick"
                    }
                    setupRecyclerViewTopPick(topPickList)
                    val mightList = travelModel.filter {
                        it.category == "mightneed"
                    }
                    setupRecyclerViewMight(mightList)
                }
            } catch (e: Exception) {
                Log.e(TAG, "onViewCreated: ex ${e.message}")
            }
        }
    }


    /**
     * [setupRecyclerViewBookmark] -> [newList] as TravelModel
     * using dataBinding set adapter [topPickAdapter]
     * using dataBinding set adapter [mightAdapter]
     */

    private fun setupRecyclerViewTopPick(newList: List<TravelModel>) {

        activity?.let { activity ->

            binding.grids3.layoutManager = GridLayoutManager(
                activity.applicationContext,
                1,
                GridLayoutManager.HORIZONTAL,
                false

            )


            topPickAdapter = TopPickAdapter(activity, newList)
            binding.grids3.adapter = topPickAdapter
        }
    }

    private fun setupRecyclerViewMight(newList: List<TravelModel>) {

        activity?.let { activity ->
            binding.grids2.layoutManager = GridLayoutManager(
                activity.applicationContext,
                1,
                GridLayoutManager.HORIZONTAL,
                false
            )

            mightAdapter = MightAdapter(activity, newList)
            binding.grids2.adapter = mightAdapter
        }
    }

}


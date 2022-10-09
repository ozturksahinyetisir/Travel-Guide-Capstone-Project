package com.ozturksahinyetisir.travelguideapp.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.adapters.RecyclerAdapter
import com.ozturksahinyetisir.travelguideapp.data.service.TravelApi
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentFlightsBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightsFragment : Fragment() {
    private val travelApi = TravelApi()
    private var recyclerAdapter: RecyclerAdapter? = null
    private var travelModel: ArrayList<TravelModel>? = null
    private lateinit var binding: FragmentFlightsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFlightsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        activity?.let{
                activity->
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(
                activity.applicationContext,
                1,
                GridLayoutManager.HORIZONTAL,
                false
            )
            binding.grids.layoutManager = layoutManager
        }
    }
    /**
     * run [getData] for call TravelModel model & if category match with other, set [TravelModel].
     */
    fun getData() {
        val call = travelApi.getTravelData()
        call.enqueue(object : Callback<List<TravelModel>> {
            override fun onResponse(
                call: Call<List<TravelModel>>,
                response: Response<List<TravelModel>>
            ) {
                if (response.isSuccessful) {
                    response.body().let {responseList ->
                        travelModel = ArrayList(responseList)
                        travelModel?.let {travelModel->
                            activity?.let { activity->

                                val flightsList = travelModel.filter {
                                    it.category == "flight"
                                }
                                setupRecyclerViewFlight(flightsList as ArrayList<TravelModel> )
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<TravelModel>>, t: Throwable) {
                Log.v("API Failure", t.message.toString())
            }
        })
    }

    private fun setupRecyclerViewFlight(newList: ArrayList<TravelModel>) {

        activity?.let { activity ->
            recyclerAdapter = RecyclerAdapter(activity, newList)
            binding.grids.adapter = recyclerAdapter
        }

    }
}


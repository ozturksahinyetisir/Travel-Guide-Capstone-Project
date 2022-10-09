package com.ozturksahinyetisir.travelguideapp.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozturksahinyetisir.travelguideapp.adapters.RecyclerAdapter
import com.ozturksahinyetisir.travelguideapp.data.service.TravelApi
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentAllBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllFragment : Fragment() {
    val TAG="AllFragment"

    private val travelApi = TravelApi()
    private var recyclerAdapter: RecyclerAdapter? = null
    private lateinit var binding: FragmentAllBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(layoutInflater)

        return binding.root
    }

    val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainScope.launch {
            try {
                getData()
                activity?.let { activity ->
                    val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(
                        activity.applicationContext,
                        1,
                        GridLayoutManager.HORIZONTAL,
                        false
                    )
                    binding.grids.layoutManager = layoutManager

                }
            }catch (e:Exception){
                Log.e(TAG, "onViewCreated: ${e.message}", )
            }
            

        }

    }


    fun getData() {
        val call = travelApi.getTravelData()
        call.enqueue(object : Callback<List<TravelModel>> {
            override fun onResponse(
                call: Call<List<TravelModel>>,
                response: Response<List<TravelModel>>
            ) {
                if (response.isSuccessful) {
                    response.body().let { travelList ->
                        travelList?.let {
                            activity?.let { activity ->
                                recyclerAdapter = RecyclerAdapter(activity, travelList)
                                binding.grids.adapter = recyclerAdapter
                                binding.grids.setHasFixedSize(true)
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
}

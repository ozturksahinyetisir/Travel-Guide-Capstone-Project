package com.ozturksahinyetisir.travelguideapp.data.service

import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import retrofit2.Call
import retrofit2.http.GET

interface TravelAPIService {

    @GET("AllTravelList")
    fun getProperties():
            Call<List<TravelModel>>

    @GET("AllTravelList")
    fun getDataProperties():
            Call<List<TravelModel>>


}

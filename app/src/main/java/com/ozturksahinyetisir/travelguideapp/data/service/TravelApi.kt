package com.ozturksahinyetisir.travelguideapp.data.service
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TravelApi {

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(TravelAPIService::class.java)

    fun getTravelData(): Call<List<TravelModel>> {
        return retrofit.getProperties()
    }
}
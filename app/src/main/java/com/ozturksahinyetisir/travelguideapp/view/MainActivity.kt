package com.ozturksahinyetisir.travelguideapp.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ozturksahinyetisir.travelguideapp.R
import com.ozturksahinyetisir.travelguideapp.adapters.RecyclerAdapter
import com.ozturksahinyetisir.travelguideapp.data.service.TravelApi
import com.ozturksahinyetisir.travelguideapp.databinding.ActivityMainBinding
import com.ozturksahinyetisir.travelguideapp.domain.model.TravelModel
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase
import com.ozturksahinyetisir.travelguideapp.view.Search.SearchFragment
import com.ozturksahinyetisir.travelguideapp.view.Trip.TripFragment
import com.ozturksahinyetisir.travelguideapp.view.guide.GuideFragment
import com.ozturksahinyetisir.travelguideapp.view.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var preferences: SharedPreferences
    private lateinit var bottomNavView: BottomNavigationView
    private val TAG = "MainActivity"
    val homeFragment = HomeFragment()
    val guideFragment = GuideFragment()
    val searchFragment = SearchFragment()
    val tripFragment = TripFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getSharedPreferences("tripdata", MODE_PRIVATE)
        getData()
        bottomNavView = binding.bottomNavigation
        /**
         * setDefaultNightMode removes night mode but slows down the app.
         */
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        /**
         * [setCurrentFragment] set first fragment as [homeFragment]
         * [setOnItemSelectedListener] uses nav menu items to change fragments.
         */
        setCurrentFragment(homeFragment)
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    setCurrentFragment(homeFragment)
                }
                R.id.nav_search -> {
                    setCurrentFragment(searchFragment)
                }
                R.id.nav_trip -> {
                    setCurrentFragment(tripFragment)
                }
                R.id.nav_guide -> {
                    setCurrentFragment(guideFragment)
                }
            }
            true
        }

    }

    private val travelApi = TravelApi()

    /**
     * [getData] function get data onCreate MainActivity
     */
    fun getData() {
        Log.e(TAG, "getData: ", )
        val call = travelApi.getTravelData()
        call.enqueue(object : Callback<List<TravelModel>> {
            override fun onResponse(
                call: Call<List<TravelModel>>,
                response: Response<List<TravelModel>>
            ) {
                if (response.isSuccessful) {
                    response.body().let { travelList ->
                        travelList?.let {
                            travelList.forEach {
                            }
                            /**
                             * try catch get TravelData into TravelDatabase
                             * catch for exception error message about TravelDatabase version & schema
                             */
                            try {
                                val data = TravelDatabase.getDatabase(this@MainActivity)
                                data.roomDao().insertTravel(it)
                                data.roomDao().getTravel()
                                Log.e(TAG, "got travel data", )
                            }catch (e:Exception){
                                Log.e(TAG, "onResponse: exception message ${e.message}", )
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

    /**
     * [setCurrentFragment] replace new fragment into flFragment
     * [getSupportFragmentManager] can usable in Activity.
     *
     */

    private fun setCurrentFragment(fragment: Fragment) {

        this.supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }


}
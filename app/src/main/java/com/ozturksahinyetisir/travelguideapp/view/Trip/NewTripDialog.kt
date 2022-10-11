package com.ozturksahinyetisir.travelguideapp.view.Trip

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ozturksahinyetisir.travelguideapp.databinding.FragmentCustomDialogBinding
import com.ozturksahinyetisir.travelguideapp.room.TravelDatabase

class NewTripDialog(context: Context) : Dialog(context) {
    //,android.R.style.Theme_Material_NoActionBar_Fullscreen) {
    val TAG = "NewTripDialog"
    private lateinit var binding: FragmentCustomDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /**
         * get calender date as i,i2,i3 = YYYY/MM/DD
         */
        val calender = binding.calendarView
        calender.setOnDateChangeListener { calenderView, i, i2, i3 ->
            val datetext = "$i3/$i2/$i"
            binding.tripDate.text = datetext
        }

        context?.let { activity ->
            var customList2 = ArrayList<String>()

            val data = TravelDatabase.getDatabase(activity)
            data.roomDao().getTravel().forEach { travelModel ->
                val itemId = travelModel.title
                customList2.addAll(listOf(itemId))
            }

            val arrayAdapter =
                ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, customList2)
            binding.spinner.adapter = arrayAdapter
            binding.spinner.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selectedItem = p0?.getItemAtPosition(p2).toString()
                    binding.textView.text = selectedItem
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
            /**
             * [saveTrip] setOnClickListener uses sharedPreferences to get trip name & date.
             * Set this trip & date to text views.
             * And dismiss dialog.
             * dialog dismiss listener opens new data immediately.
             */
            binding.saveTrip.setOnClickListener {
                var preferences = context.applicationContext.getSharedPreferences(
                    "tripdata",
                    Context.MODE_PRIVATE
                )
                var editor = preferences.edit()

                var getTrip = binding.textView.text.toString()
                var getDate = binding.tripDate.text.toString()
                editor.putString("trip", "$getTrip").apply()
                editor.putString("date", "$getDate").apply()

                Toast.makeText(context, "Trip Successfully Set", Toast.LENGTH_LONG).show()
                dismiss()

            }
        }
    }
}

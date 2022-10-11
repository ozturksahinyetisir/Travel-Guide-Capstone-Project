package com.ozturksahinyetisir.travelguideapp.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ozturksahinyetisir.travelguideapp.databinding.DialogFullScreenBinding
import com.squareup.picasso.Picasso

class FullscreenImageDialog(context: Context,var img_src:String) :Dialog(context,android.R.style.Theme_Material_NoActionBar_Fullscreen){

    lateinit var binding: DialogFullScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Picasso.get().load(img_src).into(binding.imageView)

        binding.imageView.setOnClickListener {
            dismiss()
        }

    }
}
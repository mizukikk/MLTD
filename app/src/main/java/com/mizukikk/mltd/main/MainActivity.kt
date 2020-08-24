package com.mizukikk.mltd.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mizukikk.mltd.Inject
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Event
import com.mizukikk.mltd.databinding.ActivityMainBinding
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.aaa3.setOnClickListener {
            Inject.providerMLTDRepository()
                .getAllEvent(object : ResponseCallBack<List<Event.EventResponse>>() {
                    override fun success(
                        response: List<Event.EventResponse>,
                        call: Call<List<Event.EventResponse>>
                    ) {
                        Log.d(TAG, "success: aaa3$response")
                    }

                    override fun fail(
                        errorMessage: String,
                        errorCode: Int?,
                        call: Call<List<Event.EventResponse>>
                    ) {
                        Log.d(TAG, "fail: aaa3")
                    }
                })
        }
    }
}

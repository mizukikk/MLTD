package com.mizukikk.mltd

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mizukikk.mltd.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    companion object {
        fun newIntent(activity: Activity) =
            Intent(activity, AboutActivity::class.java)
    }

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        binding.tvApiBy.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.matsurihi.me/docs/"))
            startActivity(intent)
        }
    }
}
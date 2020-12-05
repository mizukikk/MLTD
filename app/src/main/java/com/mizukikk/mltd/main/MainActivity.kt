package com.mizukikk.mltd.main

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mizukikk.mltd.AboutActivity
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.ActivityMainBinding
import com.mizukikk.mltd.main.idol.IdolFragment
import com.mizukikk.mltd.main.idol.IdolListFragment
import com.mizukikk.mltd.picture.PictureActivity
import com.mizukikk.mltd.room.query.IdolItem

class MainActivity : AppCompatActivity(), InteractiveMainActivity {

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    private val idolListFragment by lazy { IdolListFragment.newInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        setListener()
        setHomeFragment()
    }

    private fun setListener() {
        binding.about.root.setOnClickListener {
            binding.drawableLayout.closeDrawer(GravityCompat.START)
            val intent = AboutActivity.newIntent(this)
            startActivity(intent)
        }
        binding.card.root.setOnClickListener {
            binding.drawableLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun initView() {
        binding.card.tvFunction.text = getString(R.string.nav_main_card)
        binding.card.ivIcon.setImageResource(R.drawable.ic_people)
        binding.about.tvFunction.text = getString(R.string.nav_main_about)
        binding.about.ivIcon.setImageResource(R.drawable.ic_setting)
    }

    private fun initSharedElementParameter() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.sharedElementsUseOverlay = true
    }

    private val currentFragment =
        supportFragmentManager.findFragmentById(R.id.container)


    fun Fragment.begineTransaction() {
        if (this.isAdded.not())
            supportFragmentManager.beginTransaction()
                .add(R.id.container, this)
                .commit()
    }

    fun Fragment.begineTransarcionStack() {
        if (this.isAdded.not())
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, this)
                .addToBackStack(this.javaClass.simpleName)
                .commit()
    }

    fun setHomeFragment() {
        idolListFragment.begineTransaction()
    }

    override fun setIdolFragment(shareView: View, idolItem: IdolItem) {
        val fragment = IdolFragment
            .newInstance(idolItem)
        val transName = ViewCompat.getTransitionName(shareView) ?: ""
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addSharedElement(shareView, transName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }

    override fun showPhoto(
        shareView: View,
        photoUri: Uri,
        data: IdolItem
    ) {
        val transactionName = getString(R.string.activity_picture_transition_name)
        val opt = ActivityOptionsCompat
            .makeSceneTransitionAnimation(this, shareView, transactionName)
        startActivity(PictureActivity.newIntent(this, photoUri, data), opt.toBundle())
    }
}

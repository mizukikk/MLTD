package com.mizukikk.mltd.main

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mizukikk.mltd.AboutActivity
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.ActivityMainBinding
import com.mizukikk.mltd.main.idol.IdolFragment
import com.mizukikk.mltd.main.idol.IdolListFragment
import com.mizukikk.mltd.main.model.MainViewModel
import com.mizukikk.mltd.picture.PictureActivity
import com.mizukikk.mltd.room.query.IdolItem

class MainActivity : AppCompatActivity(), InteractiveMainActivity {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = MainActivity::class.java.simpleName
    private val idolListFragment by lazy { IdolListFragment.newInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViewModel()
        initView()
        setListener()
        setHomeFragment()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        viewModel.selectLangDialogEvent.observe(this, Observer { langArray ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title_card_version))
                .setAdapter(
                    ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        langArray
                    )
                ) { dialog, which ->
                    val selectLang = langArray[which]
                    binding.functions.version.tvContent.text = selectLang
                    if (viewModel.saveSelectLang(selectLang))
                        reloadCardData()
                }
                .show()
        })
    }

    private fun reloadCardData() {
        val fragment = currentFragment
        if (fragment is IdolListFragment) {
            fragment.reloadData()
        }
    }

    private fun setListener() {
        val navClick = View.OnClickListener {
            binding.drawableLayout.closeDrawer(GravityCompat.START)
            when (it?.id) {
                R.id.about -> {
                    val intent = AboutActivity.newIntent(this)
                    startActivity(intent)
                }
                R.id.card -> {
                }
                R.id.version -> {
                    viewModel.showSelectLangDialog()
                }
            }
        }
        for (i in 0 until binding.functions.llItems.childCount) {
            binding.functions.llItems.getChildAt(i).setOnClickListener(navClick)
        }
    }

    private fun initView() {
        setLockDrawable()
        initNavigation()
    }

    private fun setLockDrawable() {
        binding.drawableLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun initNavigation() {
        binding.functions.card.tvFunction.text = getString(R.string.nav_main_card)
        binding.functions.card.ivIcon.setImageResource(R.drawable.ic_people)

        binding.functions.version.ivIcon.setImageResource(R.drawable.ic_version)
        binding.functions.version.tvFunction.text = getString(R.string.nav_main_version)
        binding.functions.version.tvContent.text = viewModel.getSelectCardLang()

        binding.functions.about.tvFunction.text = getString(R.string.nav_main_about)
        binding.functions.about.ivIcon.setImageResource(R.drawable.ic_setting)
    }

    private fun initSharedElementParameter() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.sharedElementsUseOverlay = true
    }

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.container)


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

    override fun showNavMenu() {
        binding.drawableLayout.openDrawer(GravityCompat.START)
    }
}

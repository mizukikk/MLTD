package com.mizukikk.mltd.main

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
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
import com.mizukikk.mltd.main.event.EventDetailFragment
import com.mizukikk.mltd.main.event.EventListFragment
import com.mizukikk.mltd.main.event.model.EventDetailData
import com.mizukikk.mltd.main.idol.IdolFragment
import com.mizukikk.mltd.main.idol.IdolListFragment
import com.mizukikk.mltd.main.idol.service.UpdateIdolService
import com.mizukikk.mltd.main.viewmodel.MainViewModel
import com.mizukikk.mltd.picture.PictureActivity
import com.mizukikk.mltd.room.query.IdolItem

class MainActivity : AppCompatActivity(), InteractiveMainActivity {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var toast: Toast? = null
    private val TAG = MainActivity::class.java.simpleName
    private val progressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage(getString(R.string.dialog_progress))
            setCancelable(false)
        }
    }

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
        viewModel.currentEventEvent.observe(this, Observer {
            setEventDetailFragment(it)
        })
        viewModel.progressEvent.observe(this, Observer { show ->
            if (show) {
                showProgressBar()
            } else {
                dismissProgressBar()
            }
        })
        viewModel.toastEvent.observe(this, Observer { message ->
            showToast(message)
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
                R.id.updateCheck -> {
                    UpdateIdolService.start(this, 1)
                }
                R.id.eventList -> {
                    EventListFragment.newInstance().beginTransactionStack()
                }
                R.id.currentEvent -> {
                    viewModel.getCurrentEvent()
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
        //卡片
        binding.functions.card.tvFunction.text = getString(R.string.nav_main_card)
        binding.functions.card.ivIcon.setImageResource(R.drawable.ic_people)

        binding.functions.version.ivIcon.setImageResource(R.drawable.ic_version)
        binding.functions.version.tvFunction.text = getString(R.string.nav_main_version)
        binding.functions.version.tvContent.text = viewModel.getSelectCardLang()

        binding.functions.updateCheck.ivIcon.setImageResource(R.drawable.ic_update)
        binding.functions.updateCheck.tvFunction.text = getString(R.string.nav_main_check_update)
        //活動
        binding.functions.eventList.ivIcon.setImageResource(R.drawable.ic_event_rank)
        binding.functions.eventList.tvFunction.text = getString(R.string.nav_main_event)

        binding.functions.currentEvent.ivIcon.setImageResource(R.drawable.ic_event_rank)
        binding.functions.currentEvent.tvFunction.text = getString(R.string.nav_main_current_event)
        //設定
        binding.functions.about.ivIcon.setImageResource(R.drawable.ic_setting)
        binding.functions.about.tvFunction.text = getString(R.string.nav_main_about)
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

    fun Fragment.beginTransactionStack() {
        if (this.isAdded.not()) {
            val transaction = supportFragmentManager.beginTransaction()
            currentFragment?.let {
                transaction.hide(it)
            }
            transaction
                    .add(R.id.container, this)
                    .addToBackStack(this.javaClass.simpleName)
                    .setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    )
                    .commit()
        }
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

    override fun setEventDetailFragment(data: EventDetailData) {
        EventDetailFragment.newInstance(data).beginTransactionStack()
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

    override fun showProgressBar() {
        if (progressDialog.isShowing.not()) {
            progressDialog.show()
        }
    }

    override fun dismissProgressBar() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}

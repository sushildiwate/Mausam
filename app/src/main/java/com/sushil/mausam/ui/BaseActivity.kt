package com.sushil.mausam.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sushil.mausam.ApplicationActivity
import com.sushil.mausam.R
import com.sushil.mausam.customviews.BottomMenuView
import com.sushil.mausam.ui.help.HelpFragment
import com.sushil.mausam.ui.home.HomeFragment
import com.sushil.mausam.ui.settings.SettingFragment
import com.sushil.mausam.utils.HELP
import com.sushil.mausam.utils.HOME
import com.sushil.mausam.utils.SETTINGS
import kotlinx.android.synthetic.main.activity_base.*


/**
 * This is the base of the app once logged in
 * */
class BaseActivity : ApplicationActivity(), BottomMenuView.BottomMenuViewListener {

    private var mPrevSelectedMenu = -1
    private lateinit var mSelectedFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setUpBottomMenu()
        setUpToolBar()
    }


    private fun setUpBottomMenu() {
        val bottomMenuView = findViewById<BottomMenuView>(R.id.activity_home_bottom_menu_view)
        bottomMenuView.setBottomMenuViewListener(this)
        bottomMenuView.setupInitialMenu(HOME, intent.extras)
    }

    private fun setUpToolBar() {
        val actionBarToolBar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        actionBarToolBar.setBackgroundColor(Color.WHITE)
        actionBarToolBar.setTitleTextColor(Color.BLACK)
        setSupportActionBar(actionBarToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onClickMenu(menuId: Int, extras: Bundle?) {
        lateinit var fragment: Fragment
        when (menuId) {
            HOME -> {
                setTitleFragment(R.string.home)
                fragment = HomeFragment()
            }
            SETTINGS -> {
                setTitleFragment(R.string.settings)
                fragment = SettingFragment()
            }
            HELP -> {
                setTitleFragment(R.string.help)
                fragment = HelpFragment()
            }
        }

        getFragmentTag(menuId)?.let {
            replaceFragment(
                fragment = fragment,
                tag = it
            )
        }
        mPrevSelectedMenu = menuId

    }

    private fun replaceFragment(
        fragment: Fragment?,
        tag: String
    ) {
        fragment?.let {
            val fragmentManager = supportFragmentManager
            mSelectedFragment = it
            fragmentManager.beginTransaction()
                .replace(R.id.activity_home_fragment_container, it, tag).commit()
        }
    }

    private fun getFragmentTag(menuId: Int): String? {
        return "Fragment_$menuId"
    }


    private fun setTitleFragment(title: Int) {
        text_view_title.text = getString(title)
        supportActionBar?.title = getString(title)
    }


    override fun onBackPressed() {
        finish()
    }

}

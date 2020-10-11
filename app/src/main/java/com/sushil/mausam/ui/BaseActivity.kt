package com.sushil.mausam.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sushil.mausam.ApplicationActivity
import com.sushil.mausam.R
import com.sushil.mausam.customviews.BottomMenuView
import com.sushil.mausam.database.MausamDataBase
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

    private lateinit var db: MausamDataBase

    private var mPrevSelectedMenu = -1
    private var mSelectedFragment: Fragment? = null

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
        supportActionBar?.setDisplayShowTitleEnabled(false);
    }

    override fun onClickMenu(menuId: Int, extras: Bundle?) {
        var fragment: Fragment? = null
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

        replaceFragment(fragment, getFragmentTag(menuId)!!, extras)
        mPrevSelectedMenu = menuId

    }

    fun replaceFragment(
        fragment: Fragment?,
        tag: String,
        extras: Bundle?
    ) {
        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            mSelectedFragment = fragment
            fragmentManager.beginTransaction()
                .replace(R.id.activity_home_fragment_container, fragment, tag).commit()
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
        //  finish()
        exitApp()
    }

    private fun exitApp() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}

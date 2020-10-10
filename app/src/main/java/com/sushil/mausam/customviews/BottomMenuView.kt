package com.sushil.mausam.customviews

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sushil.mausam.R
import com.sushil.mausam.utils.HELP
import com.sushil.mausam.utils.HOME
import com.sushil.mausam.utils.SETTINGS

class BottomMenuView : RelativeLayout, View.OnClickListener {
    private lateinit var mTextViewHome: TextView
    private lateinit var mTextViewSettings: TextView
    private lateinit var mTextViewHelp: TextView
    private var mPrevSelectedId = -1
    private lateinit var mBottomMenuViewListener: BottomMenuViewListener

    interface BottomMenuViewListener {
        fun onClickMenu(menuId: Int, extras: Bundle?)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun setBottomMenuViewListener(bottomMenuViewListener: BottomMenuViewListener?) {
        if (bottomMenuViewListener != null) {
            mBottomMenuViewListener = bottomMenuViewListener
        }
    }

    fun setupInitialMenu(menuId: Int, extras: Bundle?) {
        onClickMenu(menuId, extras)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mTextViewHome = findViewById(R.id.textViewHome)
        mTextViewSettings = findViewById(R.id.textViewSetting)
        mTextViewHelp = findViewById(R.id.textViewHelp)
        mTextViewHome.setOnClickListener(this)
        mTextViewSettings.setOnClickListener(this)
        mTextViewHelp.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.textViewHome -> onClickMenu(HOME, null)
            R.id.textViewSetting -> onClickMenu(SETTINGS, null)
            R.id.textViewHelp -> onClickMenu(HELP, null)
        }
    }

    private fun onClickMenu(menuId: Int, extras: Bundle?) {
        if (menuId != mPrevSelectedId) {
            val textView = getMenuTextView(menuId)
            if (textView != null) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    getMenuDrawable(menuId, true),
                    0,
                    0
                )
                textView.setTextColor(ContextCompat.getColor(context, R.color.azure))
            }
            val prevTextView = getMenuTextView(mPrevSelectedId)
            if (prevTextView != null) {
                prevTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    getMenuDrawable(mPrevSelectedId, false),
                    0,
                    0
                )
                prevTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.gunmetal
                    )
                )
            }
            mPrevSelectedId = menuId
            if (mBottomMenuViewListener != null) {
                mBottomMenuViewListener.onClickMenu(menuId, extras)
            }
        }
    }

    private fun getMenuDrawable(menuId: Int, isSelected: Boolean): Int {
        when (menuId) {
            HOME -> return if (isSelected) R.drawable.ic_home_selected else R.drawable.ic_home_unselected
            SETTINGS -> return if (isSelected) R.drawable.ic_settings_selected else R.drawable.ic_settings_unselected
            HELP -> return if (isSelected) R.drawable.ic_help_selected else R.drawable.ic_help_unselected
        }
        return if (isSelected) R.drawable.ic_home_selected else R.drawable.ic_home_unselected
    }

    private fun getMenuTextView(menuId: Int): TextView? {
        when (menuId) {
            HOME -> return mTextViewHome
            SETTINGS -> return mTextViewSettings
            HELP -> return mTextViewHelp
        }
        return null
    }
}
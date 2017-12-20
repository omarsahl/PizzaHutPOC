package com.os.pizzahutpoc

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import com.os.pizzahutpoc.fragments.AboutFragment
import com.os.pizzahutpoc.fragments.FeedFragment
import com.os.pizzahutpoc.fragments.ProductsFragment
import com.os.pizzahutpoc.utils.map
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    companion object {
        private const val ALPHA_ANIMATIONS_DURATION = 250L
        private const val PERCENTAGE_TO_HIDE_FOLLOW_BUTTON = 0.2f
        private const val PERCENTAGE_TO_HIDE_WAS_THERE_LABEL = 0.5f
        private const val PERCENTAGE_TO_HIDE_FOLLOWERS_COUNT = 0.7f
        private const val PERCENTAGE_TO_HIDE_AVATAR = 0.8f
        private const val PERCENTAGE_TO_SHOW_TOOLBAR_TITLE = PERCENTAGE_TO_HIDE_AVATAR + 0.1f
    }

    private var isAvatarVisible = true
    private var isFollowButtonVisible = true
    private var isFollowerCountVisible = true
    private var isWasThereLabelVisible = true
    private var isToolbarLogoVisible = false

    private var initialToolbarTitleX = 0.0f
    /*
      * NOTE:
      * since 'kotlin-android-extensions' plugin is enabled, we can access all the views
      * directly from xml without using findViewById()
      * https://kotlinlang.org/docs/tutorials/android-plugin.html
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setNavigationIcon(R.drawable.back_icon)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        appBar.addOnOffsetChangedListener(this)
        setupViewPager(tabsViewpager)
        tabs.setupWithViewPager(tabsViewpager)

        startAlphaAnimation(toolbarLogo, 0, View.INVISIBLE)
    }

    /**
     * sets up ViewPager with ViewPagerAdapter and adds the required fragments.
     */
    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FeedFragment(), "Feed")
        adapter.addFragment(ProductsFragment(), "Products")
        adapter.addFragment(AboutFragment(), "About")
        viewPager.adapter = adapter
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val scrollRange = appBarLayout.totalScrollRange
        val percentage = Math.abs(offset).toFloat() / scrollRange.toFloat()

        updateFollowButtonAlpha(percentage)
        updateAvatarAlpha(percentage)
        updateFollowersCountAlpha(percentage)
        updateWasThereLabel(percentage)
        updateToolbarLogoAlpha(percentage)

        updateToolbarTitlePosition(percentage)
    }

    private fun updateToolbarTitlePosition(percentage: Float) {
        
    }

    private fun updateFollowButtonAlpha(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_FOLLOW_BUTTON) {
            if (isFollowButtonVisible) {
                startAlphaAnimation(followButton, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isFollowButtonVisible = false
            }

        } else {

            if (!isFollowButtonVisible) {
                startAlphaAnimation(followButton, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isFollowButtonVisible = true
            }
        }
    }

    private fun updateAvatarAlpha(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_AVATAR) {
            if (isAvatarVisible) {
                startAlphaAnimation(avatar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isAvatarVisible = false
            }
        } else {

            if (!isAvatarVisible) {
                startAlphaAnimation(avatar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isAvatarVisible = true
            }
        }
    }

    private fun updateFollowersCountAlpha(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_FOLLOWERS_COUNT) {
            if (isFollowerCountVisible) {
                startAlphaAnimation(followersCountLabel, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isFollowerCountVisible = false
            }

        } else {

            if (!isFollowerCountVisible) {
                startAlphaAnimation(followersCountLabel, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isFollowerCountVisible = true
            }
        }
    }

    private fun updateWasThereLabel(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_WAS_THERE_LABEL) {
            if (isWasThereLabelVisible) {
                startAlphaAnimation(wasThereLabel, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isWasThereLabelVisible = false
            }

        } else {

            if (!isWasThereLabelVisible) {
                startAlphaAnimation(wasThereLabel, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isWasThereLabelVisible = true
            }
        }
    }

    private fun updateToolbarLogoAlpha(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TOOLBAR_TITLE) {
            if (!isToolbarLogoVisible) {
                startAlphaAnimation(toolbarLogo, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isToolbarLogoVisible = true
            }

        } else {
            if (isToolbarLogoVisible) {
                startAlphaAnimation(toolbarLogo, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isToolbarLogoVisible = false
            }
        }
    }

    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE)
            AlphaAnimation(0f, 1f)
        else
            AlphaAnimation(1f, 0f)

        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_upload) {
            true
        } else super.onOptionsItemSelected(item)
    }
}

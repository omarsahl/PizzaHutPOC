package com.os.pizzahutpoc

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView


/**
 * Created by Omar on 19-Dec-17 8:47 PM
 */
class ImageBehaviour(context: Context, attrs: AttributeSet?) : CoordinatorLayout.Behavior<ImageView>() {

    // calculated from given layout
    private var startXPositionImage: Float = 0f
    private var startYPositionImage: Float = 0f
    private var startHeight: Int = 0
    private var startToolbarHeight: Int = 0

    private var initialised = false

    private var amountOfToolbarToMove: Float = 0.toFloat()
    private var amountOfImageToReduce: Float = 0.toFloat()
    private var amountToMoveXPosition: Float = 0.toFloat()
    private var amountToMoveYPosition: Float = 0.toFloat()

    // user configured params
    private var finalToolbarHeight: Float = 0.toFloat()
    private var finalXPosition: Float = 0.toFloat()
    private var finalYPosition: Float = 0.toFloat()
    private var finalHeight: Float = 0.toFloat()

//    init {
//
//        if (attrs != null) {
//            val a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior)
//            finalXPosition = a.getDimension(R.styleable.AvatarImageBehavior_finalXPosition, 0f)
//            finalYPosition = a.getDimension(R.styleable.AvatarImageBehavior_finalYPosition, 0f)
//            finalHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalHeight, 0f)
//            finalToolbarHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalToolbarHeight, 0f)
//            a.recycle()
//        }
//    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: ImageView, dependency: View): Boolean {
        return dependency is AppBarLayout // change if you want another sibling to depend on
    }

    override fun onDependentViewChanged(
            parent: CoordinatorLayout,
            child: ImageView,
            dependency: View): Boolean {

        // make child (avatar) change in relation to dependency (toolbar) in both size and position, init with properties from layout
        initProperties(child, dependency)

        // calculate progress of movement of dependency
        var currentToolbarHeight = startToolbarHeight + dependency.y // current expanded height of toolbar
        // don't go below configured min height for calculations (it does go passed the toolbar)
        currentToolbarHeight = if (currentToolbarHeight < finalToolbarHeight) finalToolbarHeight else currentToolbarHeight
        val amountAlreadyMoved = startToolbarHeight - currentToolbarHeight
        val progress = 100 * amountAlreadyMoved / amountOfToolbarToMove // how much % of expand we reached

        // update image size
        val heightToSubtract = progress * amountOfImageToReduce / 100
        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.width = (startHeight - heightToSubtract).toInt()
        lp.height = (startHeight - heightToSubtract).toInt()
        child.layoutParams = lp

        // update image position
        val distanceXToSubtract = progress * amountToMoveXPosition / 100
        val distanceYToSubtract = progress * amountToMoveYPosition / 100
        val newXPosition = startXPositionImage - distanceXToSubtract
        //newXPosition = newXPosition < endXPosition ? endXPosition : newXPosition; // don't go passed end position
        child.x = newXPosition
        child.y = startYPositionImage - distanceYToSubtract

        return true
    }

    private fun initProperties(
            child: ImageView,
            dependency: View) {

        if (!initialised) {
            // form initial layout
            startHeight = child.height
            startXPositionImage = child.x
            startYPositionImage = child.y
            startToolbarHeight = dependency.height
            // some calculated fields
            amountOfToolbarToMove = startToolbarHeight - finalToolbarHeight
            amountOfImageToReduce = startHeight - finalHeight
            amountToMoveXPosition = startXPositionImage - finalXPosition
            amountToMoveYPosition = startYPositionImage - finalYPosition
            initialised = true
        }
    }

}
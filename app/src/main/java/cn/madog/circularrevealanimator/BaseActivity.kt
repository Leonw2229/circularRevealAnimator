package cn.madog.circularrevealanimator

import android.animation.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout

open class BaseActivity : AppCompatActivity() {

    companion object {
        const val REVEAL_POINT_Y = "reveal_y"
        const val REVEAL_POINT_X = "reveal_x"
        const val REVEAL_COLOR_START = "color_start"
    }

    private var point = arrayListOf<Int>()
    private var colorReveal = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        getIntentPointDot(intent)
    }

    override fun finish() {

        if (point.size >= 2) {
            finishAnimator(1)
        } else {
            super.finish()
        }
    }

    override fun supportFinishAfterTransition() {
        if (point.size >= 2) {
            finishAnimator(2)
        } else {
            super.supportFinishAfterTransition()
        }
    }

    override fun onBackPressed() {
        if (point.size >= 2) {
            finishAnimator(3)
        } else {
            super.onBackPressed()
        }

    }


    private fun finishAnimator(index: Int) {
        if (point.size >= 2) {
            val decorView = window.decorView as ViewGroup
            val view1 = View(this)
            decorView.addView(view1)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view1.setBackgroundColor(colorReveal)
                val animator = createRevealAnimator(true, decorView)
                animator.interpolator = AccelerateDecelerateInterpolator()
                animator.duration = 600

                val animatorSet = AnimatorSet()
                animatorSet.play(animator)
                animatorSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        decorView.alpha = 0f
                        decorView.removeView(view1)
                        point.clear()
                        when (index) {
                            1 -> finish()
                            2 -> supportFinishAfterTransition()
                            3 -> onBackPressed()
                        }
                    }
                })
                animatorSet.start()
            }
        }
    }

    private fun getIntentPointDot(intent: Intent) {
        val extraY = intent.getIntExtra(REVEAL_POINT_Y, 0)
        val extraX = intent.getIntExtra(REVEAL_POINT_X, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorReveal = intent.getIntExtra(REVEAL_COLOR_START, ContextCompat.getColor(this, R.color.colorAccent))
            point.clear()
            if ((extraX != 0) or (extraY != 0)) {
                point.add(extraX)
                point.add(extraY)
            }
        }
    }

    override fun setContentView(layoutResID: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResID, null)
        setContentView(view)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)

        val decorView = window.decorView as ViewGroup
        val view1 = View(this)

        if (point.size >= 2) {
            view1.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            view1.setBackgroundColor(colorReveal)
            decorView.addView(view1)
        }
        decorView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                decorView.viewTreeObserver.removeOnPreDrawListener(this)
                if (point.size >= 2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val animator = createRevealAnimator(false, decorView)
                        animator.interpolator = AccelerateDecelerateInterpolator()
                        animator.duration = 700

                        try {
                            val valueAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorReveal, Color.TRANSPARENT)
                            valueAnimator.duration = 500
                            valueAnimator.addUpdateListener {
                                view1.setBackgroundColor(it.animatedValue as Int)
                            }

                            val animatorSet = AnimatorSet()
                            animatorSet.play(animator).before(valueAnimator)
                            animatorSet.addListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    super.onAnimationEnd(animation)
                                    decorView.removeView(view1)
                                }
                            })
                            animatorSet.start()
                        } catch (e: Exception) {
                            Log.e("lmy", "大多数可能就是view没有设置background", e)
                        }
                    }
                }
                return false
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createRevealAnimator(isFinish: Boolean, view: View): Animator {
        val hypo = Math.hypot(view.height.toDouble(), view.width.toDouble())
        val startRadius = if (isFinish) hypo.toFloat() else 0f
        val endRadius = if (isFinish) 0f else hypo.toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(view, point[0], point[1], startRadius, endRadius)
        animator.duration = 500
        return animator
    }

    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            supportFinishAfterTransition()
        }
    }

    override fun startActivity(intent: Intent, options: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.startActivity(intent, options)
        } else {
            startActivity(intent)
        }
    }
}
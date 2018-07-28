package cn.madog.circularrevealanimator

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * 使用揭露动画启动activity
 */
fun Fragment.intentActivityReveal(view: View, clazz: Class<*>): Intent {
    return initIntent(activity!!, view, clazz)
}

private fun initIntent(activity: Activity, view: View, clazz: Class<*>): Intent {

    val location = IntArray(2)
    view.getLocationOnScreen(location)

    var rgb = ContextCompat.getColor(activity, R.color.colorAccent)
    try {

        if (view is ImageView) {
            val drawable = view.drawable as BitmapDrawable
            val palette = Palette.from(drawable.bitmap)
            val generate = palette.generate()
            rgb = getRgb(generate, activity)

            location[0] = location[0] + view.width / 2
        } else {
            val background = view.background
            if (background is BitmapDrawable) {
                val palette = Palette.from(background.bitmap)
                val generate = palette.generate()
                getRgb(generate, activity)

                location[0] = location[0] + view.width / 2

            } else if (background is ColorDrawable) {
                rgb = if (view is TextView) {
                    if (view.compoundDrawables.isNotEmpty()) {
                        val bitmapDrawable = when {
                            view.compoundDrawables[0] != null -> {
                                location[0] = view.paddingLeft + view.compoundDrawablePadding
                                view.compoundDrawables[0] as BitmapDrawable
                            }
                            view.compoundDrawables[1] != null -> {
                                location[0] = location[0] + view.width / 2
                                view.compoundDrawables[1] as BitmapDrawable
                            }
                            view.compoundDrawables[2] != null -> {
                                location[0] = view.paddingRight + view.compoundDrawablePadding
                                view.compoundDrawables[2] as BitmapDrawable
                            }
                            view.compoundDrawables[3] != null -> {
                                location[0] = location[0] + view.width / 2
                                view.compoundDrawables[3] as BitmapDrawable
                            }
                            else -> {
                                BitmapDrawable()
                            }
                        }

                        val palette = Palette.from(bitmapDrawable.bitmap)
                        val generate = palette.generate()
                        getRgb(generate, activity)
                    } else {
                        background.color
                    }

                } else {
                    background.color
                }
            }
        }

    } catch (e: Exception) {
        Log.e("lmy", "没有读取到颜色或者背景转换失败了")
    }


    val intent = Intent(activity, clazz)
    intent.putExtra(BaseActivity.REVEAL_POINT_X, location[0])
    intent.putExtra(BaseActivity.REVEAL_POINT_Y, location[1] + (view.height / 2))
    intent.putExtra(BaseActivity.REVEAL_COLOR_START, rgb)
    return intent
}

private fun getRgb(palette: Palette, activity: Activity): Int {

    return when {
        palette.lightVibrantSwatch != null -> {
            palette.lightVibrantSwatch?.rgb!!
        }
        palette.lightMutedSwatch != null -> {
            palette.lightMutedSwatch?.rgb!!
        }
        palette.vibrantSwatch != null -> {
            palette.vibrantSwatch?.rgb!!
        }
        palette.darkVibrantSwatch != null -> {
            palette.darkVibrantSwatch?.rgb!!
        }
        palette.darkMutedSwatch != null -> {
            palette.darkMutedSwatch?.rgb!!
        }
        palette.dominantSwatch != null -> {
            palette.dominantSwatch?.rgb!!
        }
        else -> {
            ContextCompat.getColor(activity, R.color.colorAccent)
        }
    }
}

/**
 * 使用揭露动画启动activity
 */
fun Activity.intentActivityReveal(view: View, clazz: Class<*>): Intent {
    return initIntent(this, view, clazz)
}
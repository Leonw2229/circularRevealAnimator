package cn.madog.circularrevealanimator

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File

private val option = RequestOptions().placeholder(R.drawable.icon_rect_live_video)
        .error(R.drawable.icon_rect_cart)

fun ImageView.load(resId: Int) {
    Glide.with(this).load(resId).transition(DrawableTransitionOptions().crossFade(500))
            .apply(option)
            .into(this)
}

fun ImageView.load(resId: String) {
    Glide.with(this).load(resId).transition(DrawableTransitionOptions().crossFade(500))
            .apply(option)
            .into(this)
}

fun ImageView.load(resId: File) {
    Glide.with(this).load(resId).transition(DrawableTransitionOptions().crossFade(500))
            .apply(option)
            .into(this)
}

fun ImageView.load(resId: Bitmap) {
    Glide.with(this).load(resId).transition(DrawableTransitionOptions().crossFade(500))
            .apply(option)
            .into(this)
}

fun ImageView.load(uri: Uri) {
    Glide.with(this).load(uri).transition(DrawableTransitionOptions().crossFade(500))
            .apply(option)
            .into(this)
}

fun View.setBackground(any: Any) {
    Glide.with(this).load(any).into(object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            ViewCompat.setBackground(this@setBackground, resource)
        }
    })
}

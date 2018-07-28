package cn.madog.circularrevealanimator

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zhouwei.blurlibrary.EasyBlur
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAvatarBackground(R.drawable.avatar)
        initListener()
    }


    private fun initListener() {

        viewSetting.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewSetting, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }

        viewCart.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewCart, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

        }
        viewVideo.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewVideoIcon, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

        }

        viewClasses.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewClasses, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
        viewList.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewList, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
        viewOrderManager.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewOrderManager, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
        viewUserInfo.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewUserInfo, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
        viewOnlineService.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewOnlineService, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
        viewLocation.setOnClickListener {
            val intentActivityReveal = intentActivityReveal(viewLocation, OrderManagerActivity::class.java)
            startActivity(intentActivityReveal, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
    }
    private fun loadAvatarBackground(resource: Any?) {

        Glide.with(this).load(resource).apply(RequestOptions()
                .centerCrop()
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar))
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        viewUserAvatar.setImageDrawable(resource)
                        val bitmap = EasyBlur.with(this@MainActivity)
                                .bitmap((resource as BitmapDrawable).bitmap)
                                .radius(10)
                                .blur()
                        viewBackground.load(bitmap)
                    }
                })
    }
}

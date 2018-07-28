package cn.madog.circularrevealanimator

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_order_manager.*

class OrderManagerActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_manager)

        setToolbar(toolbar)
        supportActionBar?.title = "Title"

    }
}
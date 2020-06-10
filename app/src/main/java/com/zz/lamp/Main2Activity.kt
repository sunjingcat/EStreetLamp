package com.zz.lamp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zz.lamp.emen.Device
import com.zz.lamp.emen.TestProvider
import kotlinx.android.synthetic.main.activity_main2.*

@TestProvider(id = 1, type = "message", json = Device::class)
class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        bt.setOnClickListener {
            tv.setText("变化的Tv")
            val clazz = Main2Activity::class.java
            val annotation = clazz.getAnnotation(TestProvider::class.java)
            println("类注解的值：${annotation?.id},${annotation?.type}")
        }

    }
}

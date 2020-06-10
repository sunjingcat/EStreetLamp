package com.zz.lamp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zz.lamp.emen.Device
import com.zz.lamp.emen.TestProvider
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.reflect.KClass

@TestProvider(id = 1, type = "message", json = Device::class)
class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        bt.setOnClickListener {
            tv.setText("变化的Tv")
            try {

                val cls = Class.forName("com.zz.lamp.emen.TestProvider")
                val testProvider= TestProvider::class
                testProvider::id
            } catch (e: Exception) {
            }
        }

    }
}

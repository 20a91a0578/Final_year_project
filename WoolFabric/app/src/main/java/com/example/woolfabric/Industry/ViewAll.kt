package com.example.woolfabric.Industry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woolfabric.Adapter.interfere
import com.example.woolfabric.Farmer.Fragment.Products
import com.example.woolfabric.R

class ViewAll : AppCompatActivity() , interfere {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        supportFragmentManager.beginTransaction().replace(R.id.frame123,Products()).commit()
    }

    override fun view(view: Int) {

    }
}
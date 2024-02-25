package com.example.woolfabric.Farmer

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import coil.load
import com.example.woolfabric.Adapter.ViewAdapter
import com.example.woolfabric.Adapter.interfere
import com.example.woolfabric.Farmer.Functions.UpdateMe
import com.example.woolfabric.MainActivity
import com.example.woolfabric.R
import com.example.woolfabric.databinding.FarmerActivityMain3Binding
import com.google.android.material.tabs.TabLayout

class Farmer_MainActivity : AppCompatActivity(), interfere {
    private lateinit var bind:FarmerActivityMain3Binding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= FarmerActivityMain3Binding.inflate(layoutInflater)
        setContentView(bind.root)
        val tab=bind.tabLayout
        tab.apply {
            addTab(newTab().setText("Info").setIcon(R.drawable.sheep))
            addTab(newTab().setText("Products").setIcon(R.drawable.search))
            addTab(newTab().setText("Requests").setIcon(R.drawable.doc))
        tabGravity= TabLayout.GRAVITY_FILL
        }
    getSharedPreferences("user", MODE_PRIVATE)?.let {
                    bind.imageView3.load(it.getString("image","")){
                crossfade(true)
                crossfade(500)
            }
            bind.titlefarmer.text = "Hi ${it.getString("name","")}!!"
        }
        bind.imageView3.setOnClickListener {
            startActivity(Intent(this,UpdateMe::class.java))
        }



        val viewpager=bind.viewpager
        viewpager.adapter=  ViewAdapter(tab.tabCount,supportFragmentManager)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab))
        tab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
             viewpager.currentItem = tab!!.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
       viewpager.currentItem = tab!!.position
            }
        })


    }



    override fun view(view: Int) {
                bind.linearlay.isVisible=view<= 1
         }
}
package com.example.woolfabric

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import coil.annotation.ExperimentalCoilApi
import coil.load
import coil.transition.Transition
import com.example.woolfabric.Admin.Admin_mainActivity
import com.example.woolfabric.Farmer.Farmer_MainActivity
import com.example.woolfabric.Industry.Industry_MainActivity
import com.example.woolfabric.User.UserMainActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image=findViewById<CardView>(R.id.logo)
        val imaegview1243=findViewById<ImageView>(R.id.imaegview1243)
        val type=getSharedPreferences("user", MODE_PRIVATE).getString("type","")
        image.alpha=0F

        image.animate().alpha(1F).setDuration(2000).withEndAction {
            when (type) {
                "admin" -> {
                    startActivity(Intent(this,Admin_mainActivity::class.java))
                }
                "Farmer" -> {
                    startActivity(Intent(this, Farmer_MainActivity::class.java))
                }
                "Industry" -> {
                    startActivity(Intent(this,Industry_MainActivity::class.java))
                }
                "user" -> {
                    startActivity(Intent(this,UserMainActivity::class.java))
                }
                else -> {
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }
            finish()
        }.withStartAction {
            imaegview1243.load(R.drawable.logoforwoll){
                crossfade(true)
                crossfade(2000)
            }
        }

    }


}
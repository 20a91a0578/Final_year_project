package com.example.woolfabric.Admin

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.woolfabric.Admin.Fargments.Info
import com.example.woolfabric.Admin.Fargments.Management
import com.example.woolfabric.AllFunction
import com.example.woolfabric.LoginActivity
import com.example.woolfabric.MainActivity
import com.example.woolfabric.R
import com.example.woolfabric.Responses.LoginResponse
import com.example.woolfabric.Responses.Models.User
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityAdminMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin_mainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityAdminMainBinding
    lateinit var info: Info
    lateinit var management: Management
    @SuppressLint("RtlHardcoded", "ResourceAsColor", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val draw=bind.drawer
            draw.setScrimColor(Color.TRANSPARENT)
    bind.imagedrawer.setOnClickListener { draw.openDrawer(Gravity.RIGHT) }

        info=Info()
        management= Management()
        viewfragemnet(info)



bind.naviview.setNavigationItemSelectedListener {
    when(it.itemId){
        R.id.home->{
            bind.bottomNavigationView.selectedItemId=R.id.info
          bind.drawer.closeDrawers()
        }
                R.id.adduser->{

                    bind.bottomNavigationView.selectedItemId=R.id.managment
                    bind.drawer.closeDrawers()
                }
                R.id.logout->{
                    dilaog()
                    bind.drawer.closeDrawers()
                }
    }

    true
}


bind.bottomNavigationView.setOnItemSelectedListener {
    when(it.itemId) {
        R.id.info -> {
        viewfragemnet(info)
        }
        R.id.managment -> {
viewfragemnet(management)
        }
    }
    true
}
    }

    private fun dilaog() {
        AlertDialog.Builder(this).apply {
            setTheme(R.style.dialog)
            setTitle("Do you want to logout ??")
            setMessage("Press 'Yes' to Logout or Press 'No' for Cancel ")
        setPositiveButton("Yes",){dialog,_->
            dialog.dismiss()
            getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
            finishAffinity()
            startActivity(Intent(this@Admin_mainActivity,MainActivity::class.java))
        }
            setNegativeButton("No"){dialog,_->
                dialog.dismiss()
            }

            setCancelable(false)

            show()
        }
    }

    private fun viewfragemnet(fargment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,fargment).addToBackStack("Back").commit()
    }
}
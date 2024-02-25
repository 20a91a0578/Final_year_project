package com.example.woolfabric.Industry

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import coil.load
import com.example.woolfabric.Industry.Fargment.MyRequests
import com.example.woolfabric.Industry.Fargment.Requestreceived
import com.example.woolfabric.MainActivity
import com.example.woolfabric.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Industry_MainActivity : AppCompatActivity() {
    private val myRequests by lazy {
        MyRequests()
    }
    private val request by lazy {
        Requestreceived()
    }
    @SuppressLint("SuspiciousIndentation", "MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.industry_activity_main2)
    val bottom=findViewById<BottomNavigationView>(R.id.bottomNavigationView3)
        change(Requestreceived())
        findViewById<FloatingActionButton>(R.id.list).setOnClickListener {
            startActivity(Intent(this,OrdersActivity::class.java))
        }

        getSharedPreferences("user", MODE_PRIVATE).apply {
            findViewById<ImageView>(R.id.test).load(getString("image",""))
            findViewById<TextView>(R.id.title).text = "Hi ${getString("name","")} \uD83D\uDE0A !!"
        }
findViewById<FloatingActionButton>(R.id.logout5).setOnClickListener {
dialog()
}
    bottom.setOnItemSelectedListener {
        when(it.itemId){
    R.id.home4->{
    change(request)
    }
R.id.myrequest->{
    change(myRequests)
}
        }

        true
    }
    }

    private fun dialog() {
        AlertDialog.Builder(this).apply {
            setTheme(R.style.dialog)
            setTitle("Do you want to logout ??")
            setMessage("Press 'Yes' to Logout or Press 'No' for Cancel ")
            setPositiveButton("Yes",){dialog,_->
                dialog.dismiss()
                getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                finishAffinity()
                startActivity(Intent(this@Industry_MainActivity, MainActivity::class.java))
            }
            setNegativeButton("No"){dialog,_->
                dialog.dismiss()
            }

            setCancelable(false)

            show()
        }
    }

    private fun change(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.framelay,fragment).commit()
    }
}
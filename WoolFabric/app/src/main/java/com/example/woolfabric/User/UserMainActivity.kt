package com.example.woolfabric.User

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.woolfabric.R
import com.example.woolfabric.User.Fragment.Home
import com.example.woolfabric.User.Fragment.ProfileActivity
import com.example.woolfabric.User.Fragment.ViewOrdersActivity
import com.example.woolfabric.databinding.ActivityUserMainBinding


class UserMainActivity : AppCompatActivity() {
lateinit var home: Home
    private lateinit var bind:ActivityUserMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind= ActivityUserMainBinding.inflate(layoutInflater)

        setContentView(bind.root)
val name=getSharedPreferences("user", MODE_PRIVATE).getString("name","")
        supportActionBar?.let {
            title="Hi $name !!"
        }
        home=Home()
        replace(Home())

        bind.bottomNavigationView2.setOnItemSelectedListener {
when(it.itemId){
    R.id.products->{
        replace(Home())
    }
    R.id.management->{
        replace(ViewOrdersActivity())
    }
    R.id.profile->{
        replace(ProfileActivity())
    }
}
true
        }

    }
private fun replace(fragment: Fragment){
    supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()
}







}
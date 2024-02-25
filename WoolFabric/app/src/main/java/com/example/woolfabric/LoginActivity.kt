package com.example.woolfabric

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.woolfabric.Admin.AddMembers
import com.example.woolfabric.Admin.Admin_mainActivity
import com.example.woolfabric.Farmer.Farmer_MainActivity
import com.example.woolfabric.Industry.Industry_MainActivity
import com.example.woolfabric.Responses.LoginResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.User.UserMainActivity
import com.example.woolfabric.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity:AppCompatActivity() {
    private lateinit var bind:ActivityLoginBinding
    private lateinit var allFunction: AllFunction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction= AllFunction(this)
        bind.farmerac.setOnClickListener {
            Intent(this, AddMembers::class.java).apply {
                putExtra("type","Farmer")
                startActivity(this)
            }
        }
        bind.industyac.setOnClickListener {
            Intent(this, AddMembers::class.java).apply {
                putExtra("type","Industry")
                startActivity(this)
            }
        }
    bind.signuppage.setOnClickListener { startActivity(Intent(this,Signup::class.java)) }
    bind.continue2.setOnClickListener {
            val mail=bind.email.text.toString().trim()
            val pass=bind.password.text.toString().trim()
        if(mail.isEmpty()){
            allFunction.toast("Please enter your mail")
        }else if(pass.isEmpty()){
            allFunction.toast("Please enter your password")
        }else if(mail.lowercase().contains("admin")&&pass.lowercase().contains("admin")){
            startActivity(Intent(this,Admin_mainActivity::class.java))
            getSharedPreferences("user", MODE_PRIVATE).edit().putString("type","admin").apply()
            finishAffinity()
        } else if(!mail.contains("@gmail.com")||mail.contains("admin")){
            allFunction.toast("Please enter a valid mail-id")
        }else{
            allFunction.p.show()
            CoroutineScope(IO).launch {
                RetroFit.instance.login(mail=mail, password = pass).enqueue(object :Callback<LoginResponse>{
                    @SuppressLint("SuspiciousIndentation")
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        allFunction.p.dismiss()
                                val rep=response.body()
                                    rep?.let {
                            it.apply {
                            allFunction.toast(message)
                            if(data.isNotEmpty()){
                                    val da=data[0]
                                    getSharedPreferences("user", MODE_PRIVATE).edit().apply {
                                        putString("id",da.id)
                                        putString("name",da.name)
                                        putString("mobile",da.mobile)
                                        putString("mail",da.mail)
                                        putString("password",da.password)
                                        putString("type",da.type)
                                        putString("image",da.image)
                                        putString("count",da.count)
                                        apply()
                                    }

                                when (da.type) {
                                    "user" -> {
                                        startActivity(Intent(this@LoginActivity,UserMainActivity::class.java))
                                    }
                                    "Farmer" -> {
                                        startActivity(Intent(this@LoginActivity,Farmer_MainActivity::class.java))
                                    }
                                    "Industry" -> {
                                        startActivity(Intent(this@LoginActivity,Industry_MainActivity::class.java))
                                    }
                                }
                                finishAffinity()
                                }
                        }
                        }
if(rep==null){
    allFunction.toast("${response.body()}")
}
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    allFunction.toast(t.message!!)
                    }
                })
            }
        }
    }


    }
}
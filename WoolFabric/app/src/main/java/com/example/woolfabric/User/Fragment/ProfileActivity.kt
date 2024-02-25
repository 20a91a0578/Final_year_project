package com.example.woolfabric.User.Fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import androidx.fragment.app.Fragment
import com.example.woolfabric.MainActivity
import com.example.woolfabric.R
import com.example.woolfabric.databinding.ActivityProductsBinding
import com.example.woolfabric.databinding.ActivityProfileBinding

class ProfileActivity : Fragment() {
    private lateinit var bind:ActivityProfileBinding
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bind= ActivityProfileBinding.inflate(layoutInflater)

        bind.logout4.setOnClickListener { dialog() }
        requireActivity().getSharedPreferences("user",AppCompatActivity.MODE_PRIVATE).apply {
            val text="<b>Name  : </b>${getString("name","")}<br></br>"+
                    "<b>Mail   : </b>${getString("mail","")}<br></br>" +
                    "<b>Mobile : </b>${getString("mobile","")}<br></br>"
            bind.details7.text=HtmlCompat.fromHtml(text,FROM_HTML_OPTION_USE_CSS_COLORS)
        }
        return bind.root
    }

    private fun dialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Do you want to logout ??")
            setMessage("Press 'Yes' to Logout or Press 'No' for Cancel ")
            setPositiveButton("Yes",){dialog,_->
                dialog.dismiss()
                requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().clear().apply()
                requireActivity().finishAffinity()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            setNegativeButton("No"){dialog,_->
                dialog.dismiss()
            }

            setCancelable(false)

            show()
        }
    }
}
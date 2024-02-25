package com.example.woolfabric.Farmer.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.woolfabric.Adapter.interfere
import com.example.woolfabric.Farmer.Functions.AllRequests
import com.example.woolfabric.Farmer.Functions.ViewAlIndustries
import com.example.woolfabric.Farmer.Functions.ViewMyOrders
import com.example.woolfabric.Farmer.Functions.ViewMyRequests
import com.example.woolfabric.MainActivity
import com.example.woolfabric.R
import com.example.woolfabric.databinding.ActivityOthersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Others : Fragment() {
    lateinit var interfere: interfere
    private lateinit var bind:ActivityOthersBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        interfere=activity as interfere
        interfere.view(0)

        bind=ActivityOthersBinding.inflate(layoutInflater)
        with(bind) {
            orders.setOnClickListener { startActivity(Intent(requireContext(), ViewMyOrders::class.java)) }
            logout2.setOnClickListener { dialog() }
            requests.setOnClickListener { startActivity(Intent(requireContext(), AllRequests::class.java)) }
            sendrequest.setOnClickListener { startActivity(Intent(requireContext(), ViewAlIndustries::class.java)) }
            industry.setOnClickListener { startActivity(Intent(requireContext(),ViewMyRequests::class.java)) }
        }
        return bind.root
    }

    private fun dialog() {
        val context=ContextThemeWrapper(requireContext(),R.style.themenew)
        MaterialAlertDialogBuilder(context).apply {
            setTitle("Do you want to logout ??")
            setMessage("Press 'Yes' to Logout or Press 'No' for Cancel ")
            setPositiveButton("Yes",){dialog,_->
                dialog.dismiss()
                requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().clear().apply()
                requireActivity().finishAffinity()
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
            setNegativeButton("No"){dialog,_->
                dialog.dismiss()
            }

            setCancelable(false)

            show()
        }
    }

    override fun onResume() {
        super.onResume()
    viewdata()
    }

    private fun viewdata() {

    }
}
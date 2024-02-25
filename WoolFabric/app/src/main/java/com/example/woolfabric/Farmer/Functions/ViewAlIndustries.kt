package com.example.woolfabric.Farmer.Functions

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.woolfabric.AllFunction
import com.example.woolfabric.R
import com.example.woolfabric.Responses.LoginResponse
import com.example.woolfabric.Responses.Models.User
import com.example.woolfabric.Responses.RetroFit

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.woolfabric.databinding.ActivityViewAllIndustriesBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.PolygonOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.DecimalFormat

class ViewAlIndustries : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var data2: ArrayList<User>
    private lateinit var fused: FusedLocationProviderClient
    private lateinit var binding: ActivityViewAllIndustriesBinding
    private lateinit var allfunction: AllFunction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data2 = ArrayList()
        fused = LocationServices.getFusedLocationProviderClient(this)
        allfunction = AllFunction(this)
        binding = ActivityViewAllIndustriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val permission = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(
                this,
                permission[0]
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                permission[1]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permission, 100)
        } else {
            fused.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val k = LatLng(it.latitude, it.longitude)
                    mMap.addMarker(MarkerOptions().title("Your Location").position(k))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(k, 13F))
                    mMap.setOnMarkerClickListener { the ->
                        data2.forEach {
                            if (it.name == the.title) {
                                try {
                                    mMap.addPolygon(
                                        PolygonOptions().add(k, the.position).strokeWidth(5.5f)
                                            .strokeColor(Color.GREEN).fillColor(Color.BLUE)
                                    )
                                } catch (e: Exception) {
                                    allfunction.toast("Invalid details industry of ${it.name},${e.message}")
                                }
                            }
                            AlertDialog.Builder(this).apply {

                                setTheme(R.style.dialog)
                                setTitle("Do you want to send a request for ${the.title}??")
                                setMessage("Press Yes to confirm or 'No' for cancel!!!")
                                setPositiveButton("Yes") { d, _ ->
                                    d.dismiss()
                                    Intent(this@ViewAlIndustries, SendRequest::class.java).apply {
                                        putExtra("data", it)
                                        startActivity(this)
                                    }
                                }
                                setCancelable(false)
                                setNegativeButton("No") { d, _ ->
                                    d.dismiss()
                                }
                                show()

                            }
                        }
                        true
                    }
                }
            }


            getdata()
        }


    }


    private fun getdata() {

        allfunction.p.show()
        CoroutineScope(IO).launch {
            RetroFit.instance.getIndustires(condition = "getindustries")
                .enqueue(object : Callback<LoginResponse> {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        /*in data base industry works as address of the industry*/
                        allfunction.apply {
                            response.body()?.apply {
                                data2 = data
                                data.forEach {
                                    try {
                                        val coder =
                                            Geocoder(this@ViewAlIndustries).getFromLocationName(
                                                it.count,
                                                1
                                            )[0]
                                        val formate = DecimalFormat("##.#######")
                                        val lat = formate.format(coder.latitude)
                                        val lon = formate.format(coder.longitude)
                                        mMap.addMarker(
                                            MarkerOptions().title(it.name)
                                                .position(LatLng(lat.toDouble(), lon.toDouble()))
                                                .icon(
                                                    getdirectory(
                                                        getDrawable(R.drawable.factory)!!
                                                    )
                                                )
                                        )
                                    } catch (e: Exception) {
                                        toast("Invalid details industry of ${it.name},${e.message}")
                                    }

                                }
                            }
                            if (response.body() == null) {
                                toast("Something went wrong ${response.body()}")
                            }
                            p.dismiss()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        allfunction.apply {
                            toast(t.message!!)
                            p.dismiss()
                        }
                    }
                })
        }
    }

    private fun getdirectory(drawable: Drawable): BitmapDescriptor? {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
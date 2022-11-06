package com.rivvana.kotlin_android_lbs

import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rivvana.kotlin_android_lbs.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var geocoder: Geocoder
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        geocoder = Geocoder(this, Locale.getDefault()) //initialize
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        binding.btnGetLocation.setOnClickListener {
            fetchLocation()
        }
    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION ), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null){
                val address = geocoder.getFromLocation(it.latitude ,it.longitude, 1)
                binding.tvLocation.text = "${address.get(0).getAddressLine(0)}"
                Log.e("CEK LOKASI", "${it.latitude} ${it.longitude}")
            }
        }
    }
}
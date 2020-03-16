package com.pdsi.digital.attendance.ext

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import java.lang.Exception
import java.util.*

val PERMISSION_ID = 42

fun Context.checkPermission(): Boolean {
    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) return true

    return false
}

fun Context.requestLocPermission(context: Activity) {
    ActivityCompat.requestPermissions(
        context,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        PERMISSION_ID
    )
}

fun Context.isLocationEnabled(): Boolean {
    var locationManager: LocationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun Context.getCompleteAdress(context: Activity, lat: Double, lng: Double): String {
    try {
        return Geocoder(context, Locale.getDefault()).getFromLocation(lat, lng, 1).get(0)
            .getAddressLine(0)
    } catch (e: Exception) {
        Log.d("ADDR ERR", e.toString())
        return ""
    }
}

fun Context.openMap(lat: Double, long: Double){
    val uri: String = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", lat, long)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}
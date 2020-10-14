package br.edu.jonathangs.pokmontcgdeveloper.ui.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

val Context.connectivityManager: ConnectivityManager?
    get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

fun ConnectivityManager.isOnline(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        hasNetworkCapabilities()
    else
        isNetworkConnected()
}

@RequiresApi(api = Build.VERSION_CODES.M)
private fun ConnectivityManager.hasNetworkCapabilities(): Boolean {
    val capabilities = getNetworkCapabilities(activeNetwork)
    return capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}

private fun ConnectivityManager.isNetworkConnected(): Boolean {
    return activeNetworkInfo?.isConnected == true &&
            (activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
                    || activeNetworkInfo?.subtype == ConnectivityManager.TYPE_MOBILE)
}
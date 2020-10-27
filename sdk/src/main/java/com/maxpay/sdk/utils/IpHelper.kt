package com.maxpay.sdk.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.math.BigInteger
import java.net.InetAddress

interface IpHelper {
    fun getUserIp(): String
    fun getUserIpNew(): String
}

class IpHelperImpl: IpHelper, KoinComponent {
    private val context: Context by inject()
    override fun getUserIp(): String {
        val wm =
            context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val ip: String = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)

        return ip
    }

    override fun getUserIpNew(): String {
        val wm =
            context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddressInt = wm.dhcpInfo.netmask
        val ipAddress = BigInteger.valueOf(ipAddressInt.toLong()).toByteArray()
        val myaddr = InetAddress.getByAddress(ipAddress)
        val hostaddr: String =
            myaddr.getHostAddress() // numeric representation (such as "127.0.0.1")
        return hostaddr
    }


}
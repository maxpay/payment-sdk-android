package com.maxpay.sdk.payment.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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
        return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
    }

    override fun getUserIpNew(): String {
        val wm =
            context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddressInt = wm.dhcpInfo.netmask
        val ipAddress = BigInteger.valueOf(ipAddressInt.toLong()).toByteArray()
        val myaddr = InetAddress.getByAddress(ipAddress)
        return myaddr.getHostAddress() // numeric representation (such as "127.0.0.1")
    }


}
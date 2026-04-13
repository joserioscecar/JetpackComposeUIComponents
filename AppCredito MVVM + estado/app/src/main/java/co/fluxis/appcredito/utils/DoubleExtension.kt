package co.fluxis.appcredito.utils

fun Double.format(): String =
    "$" + String.format("%,.2f", this)

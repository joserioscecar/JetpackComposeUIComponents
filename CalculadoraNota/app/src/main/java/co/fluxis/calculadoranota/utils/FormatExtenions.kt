package co.fluxis.calculadoranota.utils

fun Double.formatear(decimales: Int): String {
    return "%.${decimales}f".format(this)
}
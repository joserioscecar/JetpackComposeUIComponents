package com.example.appimc


object ImcCalculator {

    fun calcularIMC(peso: Double, altura: Double): Double? {


        if (peso <= 0 ) return null
        if (altura <= 0) return null

        return peso / (altura * altura)
    }

    fun calcularCategoria(imc: Double?): String {
        return if (imc != null) {
            when {
                imc < 18.5 -> "Bajo peso"
                imc < 25.0 -> "Normal"
                imc < 30.0 -> "Sobrepeso"
                else -> "Obesidad"
            }
        } else {
            "Sin datos" // Valor por defecto si el IMC es nulo
        }
    }

}
package com.example.martinifoodlabs_grupo3.viewmodel

/** Toda la lógica de validación vive aquí, desacoplada de la UI. null = válido. */
object Validadores {
    private val EMAIL = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val FONO = Regex("^(\\+?56)?9\\d{8}$")

    fun nombre(v: String): String? = when {
        v.isBlank() -> "Ingresa tu nombre"
        v.trim().length < 3 -> "Debe tener al menos 3 letras"
        v.any { it.isDigit() } -> "El nombre no puede tener números"
        else -> null
    }

    fun correo(v: String): String? = when {
        v.isBlank() -> "Ingresa tu correo"
        !EMAIL.matches(v.trim()) -> "Correo inválido (ej: nombre@correo.cl)"
        else -> null
    }

    fun telefono(v: String): String? = when {
        v.isBlank() -> "Ingresa tu teléfono"
        !FONO.matches(v.replace(" ", "")) -> "Formato: +56912345678"
        else -> null
    }

    fun rut(v: String): String? {
        val msg = "RUT inválido (ej: 12.345.678-5)"
        val limpio = v.replace(".", "").replace("-", "").uppercase()
        if (v.isBlank()) return "Ingresa el RUT de la empresa"
        if (limpio.length !in 8..9) return msg
        val cuerpo = limpio.dropLast(1)
        if (!cuerpo.all { it.isDigit() }) return msg
        var suma = 0
        var factor = 2
        for (c in cuerpo.reversed()) {
            suma += (c - '0') * factor
            factor = if (factor == 7) 2 else factor + 1
        }
        val esperado = when (val r = 11 - (suma % 11)) {
            11 -> '0'
            10 -> 'K'
            else -> '0' + r
        }
        return if (limpio.last() == esperado) null else msg
    }

    fun tipoNegocio(v: String): String? = if (v.isBlank()) "Selecciona el tipo de negocio" else null

    fun password(v: String): String? = when {
        v.isBlank() -> "Ingresa una contraseña"
        v.length < 6 -> "Mínimo 6 caracteres"
        !v.any { it.isDigit() } -> "Debe incluir al menos un número"
        else -> null
    }

    fun confirmar(pass: String, conf: String): String? = when {
        conf.isBlank() -> "Repite la contraseña"
        pass != conf -> "Las contraseñas no coinciden"
        else -> null
    }
}
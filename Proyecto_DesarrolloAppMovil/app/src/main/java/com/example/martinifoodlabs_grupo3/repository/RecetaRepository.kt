package com.example.martinifoodlabs_grupo3.repository

import com.example.martinifoodlabs_grupo3.model.Receta

// Contenido simulado (sin datos reales ni material interno)
object RecetaRepository {
    val categorias = listOf("Chocolatería", "Pastelería", "Heladería", "Bollería", "Masas", "Empanadas")

    val recetas = listOf(
        Receta(1, "Croissant de mantequilla", "Bollería", "90 min",
            "Laminado clásico con mantequilla de alto punto de fusión.",
            listOf("Preparar la masa base y reposar en frío.", "Incorporar la mantequilla y dar 3 pliegues simples.", "Cortar, enrollar y fermentar.", "Hornear a 190 °C hasta dorar.")),
        Receta(2, "Bombones de chocolate semiamargo", "Chocolatería", "60 min",
            "Atemperado y relleno de ganache suave.",
            listOf("Atemperar el chocolate.", "Moldear las cáscaras.", "Rellenar con ganache.", "Sellar y desmoldar.")),
        Receta(3, "Torta tres leches", "Pastelería", "75 min",
            "Bizcocho húmedo ideal para vitrina.",
            listOf("Hornear el bizcocho.", "Perforar y bañar con la mezcla de tres leches.", "Cubrir con crema y refrigerar.")),
        Receta(4, "Helado cremoso de vainilla", "Heladería", "45 min",
            "Base de crema con textura estable.",
            listOf("Cocer la base a 82 °C.", "Enfriar rápido.", "Madurar 4 horas.", "Mantecar.")),
        Receta(5, "Masa de hoja rápida", "Masas", "50 min",
            "Hojaldre simple para tartas y pasteles.",
            listOf("Mezclar harina y mantequilla fría.", "Dar pliegues dobles.", "Reposar y estirar.")),
        Receta(6, "Empanadas de queso al horno", "Empanadas", "55 min",
            "Masa crujiente con relleno cremoso.",
            listOf("Preparar la masa.", "Rellenar con queso.", "Pintar con huevo.", "Hornear a 200 °C."))
    )


    fun porId(id: Int): Receta? = recetas.firstOrNull { it.id == id }
}
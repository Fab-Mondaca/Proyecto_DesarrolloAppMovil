package com.example.martinifoodlabs_grupo3.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "preferencias_usuario")

class AjustesDataStore(private val context: Context) {
    private val TEXTO_GRANDE = booleanPreferencesKey("texto_grande")

    suspend fun guardarTextoGrande(valor: Boolean) {
        context.dataStore.edit { it[TEXTO_GRANDE] = valor }
    }

    fun obtenerTextoGrande(): Flow<Boolean?> = context.dataStore.data.map { it[TEXTO_GRANDE] }
}
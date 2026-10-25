package com.example.martinifoodlabs_grupo3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.martinifoodlabs_grupo3.model.RecetaGuardada
import com.example.martinifoodlabs_grupo3.model.Usuario

// Room = capa sobre SQLite
@Database(entities = [Usuario::class, RecetaGuardada::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun guardadaDao(): GuardadaDao

    companion object {
        @Volatile private var instancia: AppDatabase? = null

        fun get(context: Context): AppDatabase = instancia ?: synchronized(this) {
            instancia ?: Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "martini.db"
            ).fallbackToDestructiveMigration() // MVP académico: sin migraciones
                .build().also { instancia = it }
        }
    }
}
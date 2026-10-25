package com.example.martinifoodlabs_grupo3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinifoodlabs_grupo3.data.AppDatabase
import com.example.martinifoodlabs_grupo3.model.Usuario
import com.example.martinifoodlabs_grupo3.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CampoUi(val valor: String = "", val error: String? = null)

data class RegistroUiState(
    val nombre: CampoUi = CampoUi(),
    val correo: CampoUi = CampoUi(),
    val telefono: CampoUi = CampoUi(),
    val rut: CampoUi = CampoUi(),
    val tipoNegocio: CampoUi = CampoUi(),
    val password: CampoUi = CampoUi(),
    val confirmar: CampoUi = CampoUi(),
    val aceptaTerminos: Boolean = false,
    val errorTerminos: String? = null,
    val cargando: Boolean = false
) {
    val campos get() = listOf(nombre, correo, telefono, rut, tipoNegocio, password, confirmar)
}

data class LoginUiState(
    val correo: CampoUi = CampoUi(),
    val password: CampoUi = CampoUi(),
    val errorGeneral: String? = null,
    val cargando: Boolean = false
)

data class EdicionUiState(
    val nombre: CampoUi = CampoUi(),
    val telefono: CampoUi = CampoUi()
)

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.get(app)
    private val repo = UsuarioRepository(db.usuarioDao(), db.guardadaDao())

    private val _registro = MutableStateFlow(RegistroUiState())
    val registro: StateFlow<RegistroUiState> = _registro.asStateFlow()

    private val _login = MutableStateFlow(LoginUiState())
    val login: StateFlow<LoginUiState> = _login.asStateFlow()

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario.asStateFlow()

    /** Último registro exitoso (para la pantalla Resumen, ViewModel compartido - Guía 11) */
    private val _ultimoRegistro = MutableStateFlow<Usuario?>(null)
    val ultimoRegistro: StateFlow<Usuario?> = _ultimoRegistro.asStateFlow()

    private val _edicion = MutableStateFlow<EdicionUiState?>(null)
    val edicion: StateFlow<EdicionUiState?> = _edicion.asStateFlow()

    // ---------- Registro ----------
    fun onNombre(v: String) = _registro.update { it.copy(nombre = CampoUi(v, Validadores.nombre(v))) }
    fun onCorreo(v: String) = _registro.update { it.copy(correo = CampoUi(v, Validadores.correo(v))) }
    fun onTelefono(v: String) = _registro.update { it.copy(telefono = CampoUi(v, Validadores.telefono(v))) }
    fun onRut(v: String) = _registro.update { it.copy(rut = CampoUi(v, Validadores.rut(v))) }
    fun onTipoNegocio(v: String) = _registro.update { it.copy(tipoNegocio = CampoUi(v, Validadores.tipoNegocio(v))) }
    fun onPassword(v: String) = _registro.update {
        it.copy(
            password = CampoUi(v, Validadores.password(v)),
            confirmar = if (it.confirmar.valor.isEmpty()) it.confirmar
            else CampoUi(it.confirmar.valor, Validadores.confirmar(v, it.confirmar.valor))
        )
    }
    fun onConfirmar(v: String) = _registro.update {
        it.copy(confirmar = CampoUi(v, Validadores.confirmar(it.password.valor, v)))
    }
    fun onTerminos(v: Boolean) = _registro.update {
        it.copy(aceptaTerminos = v, errorTerminos = if (v) null else "Debes aceptar los términos")
    }

    fun registrar(onOk: () -> Unit) {
        val s = _registro.value
        val v = s.copy(
            nombre = s.nombre.copy(error = Validadores.nombre(s.nombre.valor)),
            correo = s.correo.copy(error = Validadores.correo(s.correo.valor)),
            telefono = s.telefono.copy(error = Validadores.telefono(s.telefono.valor)),
            rut = s.rut.copy(error = Validadores.rut(s.rut.valor)),
            tipoNegocio = s.tipoNegocio.copy(error = Validadores.tipoNegocio(s.tipoNegocio.valor)),
            password = s.password.copy(error = Validadores.password(s.password.valor)),
            confirmar = s.confirmar.copy(error = Validadores.confirmar(s.password.valor, s.confirmar.valor)),
            errorTerminos = if (s.aceptaTerminos) null else "Debes aceptar los términos"
        )
        _registro.value = v
        if (v.campos.any { it.error != null } || v.errorTerminos != null) return

        viewModelScope.launch {
            _registro.update { it.copy(cargando = true) }
            val nuevo = Usuario(
                nombre = v.nombre.valor.trim(),
                correo = v.correo.valor.trim().lowercase(),
                telefono = v.telefono.valor.trim(),
                rutEmpresa = v.rut.valor.trim(),
                tipoNegocio = v.tipoNegocio.valor,
                password = v.password.valor
            )
            if (repo.registrar(nuevo)) {
                _ultimoRegistro.value = nuevo.copy(password = "")
                _registro.value = RegistroUiState()
                onOk()
            } else {
                _registro.update {
                    it.copy(cargando = false, correo = it.correo.copy(error = "Este correo ya está registrado"))
                }
            }
        }
    }

    // ---------- Login ----------
    fun onLoginCorreo(v: String) = _login.update { it.copy(correo = CampoUi(v, Validadores.correo(v)), errorGeneral = null) }
    fun onLoginPassword(v: String) = _login.update {
        it.copy(password = CampoUi(v, if (v.isBlank()) "Ingresa tu contraseña" else null), errorGeneral = null)
    }

    fun iniciarSesion(onOk: () -> Unit) {
        val s = _login.value
        val v = s.copy(
            correo = s.correo.copy(error = Validadores.correo(s.correo.valor)),
            password = s.password.copy(error = if (s.password.valor.isBlank()) "Ingresa tu contraseña" else null)
        )
        _login.value = v
        if (v.correo.error != null || v.password.error != null) return

        viewModelScope.launch {
            _login.update { it.copy(cargando = true) }
            val u = repo.login(v.correo.valor, v.password.valor)
            if (u != null) {
                _usuario.value = u
                _login.value = LoginUiState()
                onOk()
            } else {
                _login.update { it.copy(cargando = false, errorGeneral = "Correo o contraseña incorrectos") }
            }
        }
    }

    fun cerrarSesion() { _usuario.value = null }

    // ---------- Update / Delete de la cuenta ----------
    fun abrirEdicion() {
        val u = _usuario.value ?: return
        _edicion.value = EdicionUiState(CampoUi(u.nombre), CampoUi(u.telefono))
    }
    fun cerrarEdicion() { _edicion.value = null }
    fun onEdNombre(v: String) = _edicion.update { it?.copy(nombre = CampoUi(v, Validadores.nombre(v))) }
    fun onEdTelefono(v: String) = _edicion.update { it?.copy(telefono = CampoUi(v, Validadores.telefono(v))) }

    fun guardarEdicion() {
        val e = _edicion.value ?: return
        val u = _usuario.value ?: return
        val v = e.copy(
            nombre = e.nombre.copy(error = Validadores.nombre(e.nombre.valor)),
            telefono = e.telefono.copy(error = Validadores.telefono(e.telefono.valor))
        )
        _edicion.value = v
        if (v.nombre.error != null || v.telefono.error != null) return
        viewModelScope.launch {
            val nuevo = u.copy(nombre = v.nombre.valor.trim(), telefono = v.telefono.valor.trim())
            repo.actualizar(nuevo)
            _usuario.value = nuevo
            _edicion.value = null
        }
    }

    fun eliminarCuenta(onOk: () -> Unit) {
        val u = _usuario.value ?: return
        viewModelScope.launch {
            repo.eliminar(u)
            _usuario.value = null
            onOk()
        }
    }
}
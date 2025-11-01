package kg.alatoo.onlinestore.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.alatoo.onlinestore.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val result = authRepository.login(username, password)
            _loginState.value = if (result.isSuccess) {
                LoginState.Success(result.getOrNull()!!)
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "Ошибка")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: kg.alatoo.onlinestore.data.remote.dto.LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}
package fr.onat.turboplant.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.api.ArchiApi
import fr.onat.turboplant.models.Credentials
import fr.onat.turboplant.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun updateEmail(email: String) = _email.update { email }

    fun updatePassword(password: String) = _password.update { password }

    fun sendLoginRequest(onResponse: (Boolean) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.loginRequest(
                Credentials(
                    email = email.value,
                    password = password.value
                ),
                onResponse = { onResponse(it) }
            )
        }
}
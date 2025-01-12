package fr.onat.turboplant.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.models.LoginDetails
import fr.onat.turboplant.data.repositories.AuthRepository
import fr.onat.turboplant.libs.extensions.asyncLaunch
import fr.onat.turboplant.libs.extensions.getMessage
import fr.onat.turboplant.models.RegistrationDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.sign_in
import turboplant.composeapp.generated.resources.sign_up

enum class SignMode(val labelRes: StringResource) {
    IN(Res.string.sign_in),
    UP(Res.string.sign_up)
}

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val isAuthenticated = authRepository.isAuthenticated()

    private val _signMode = MutableStateFlow(SignMode.IN)
    val signMode = _signMode.asStateFlow()

    private val _loginDetails = MutableStateFlow(LoginDetails())
    val loginDetails = _loginDetails.asStateFlow()

    private val _registrationDetails = MutableStateFlow(RegistrationDetails())
    val registrationDetails = _registrationDetails.asStateFlow()

    fun updateSignMode(signMode: SignMode) = _signMode.update { signMode }

    fun updateLoginDetails(details: LoginDetails) = _loginDetails.update { details }

    fun updateRegistrationDetails(details: RegistrationDetails) =
        _registrationDetails.update { details }

    fun sendLoginRequest(showSnackBarMessage: (String?) -> Unit) = asyncLaunch {
        authRepository.loginRequest(
            loginDetails = loginDetails.value,
            onFailure = { asyncLaunch { showSnackBarMessage(it.getMessage()) } }
        )
    }

    fun sendRegistrationRequest(showSnackBarMessage: (String?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            if (registrationDetails.value.password != registrationDetails.value.confirmationPassword) return@launch
            authRepository.registrationRequest(
                registrationDetails = registrationDetails.value,
                onSuccess = { asyncLaunch { showSnackBarMessage(it.getMessage()) } },
                onFailure = { asyncLaunch { showSnackBarMessage(it.getMessage()) } }
            )
        }
}
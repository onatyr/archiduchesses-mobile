package fr.onat.turboplant.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.libs.extensions.collectAsEffect
import fr.onat.turboplant.libs.utils.LocalSnackbarHostState
import fr.onat.turboplant.models.LoginDetails
import fr.onat.turboplant.models.RegistrationDetails
import fr.onat.turboplant.resources.Colors
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.login
import turboplant.composeapp.generated.resources.plant_icon
import turboplant.composeapp.generated.resources.register
import turboplant.composeapp.generated.resources.welcome_sub_title
import turboplant.composeapp.generated.resources.welcome_turbo_plant

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel(),
    navigate: () -> Unit
) {
    val signMode by viewModel.signMode.collectAsStateWithLifecycle()
    val loginDetails by viewModel.loginDetails.collectAsStateWithLifecycle()
    val registrationDetails by viewModel.registrationDetails.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current
    val showSnackBarMessage: (String?) -> Unit = {
        scope.launch {
            snackbarHostState.showSnackbar(it ?: "")
        }
    }

    viewModel.isAuthenticated.collectAsEffect {
        if (it) navigate()
    }
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AuthHeader()
        SignModeToggle(currentSignMode = signMode, updateSignMode = viewModel::updateSignMode)
        if (signMode == SignMode.IN)
            LoginForm(
                loginDetails = loginDetails,
                updateLoginDetails = viewModel::updateLoginDetails,
                validateForm = { viewModel.sendLoginRequest(showSnackBarMessage) }
            )
        else
            RegistrationForm(
                registrationDetails = registrationDetails,
                updateRegistrationDetails = viewModel::updateRegistrationDetails,
                validateForm = { viewModel.sendRegistrationRequest(showSnackBarMessage) }
            )
    }
}

@Composable
fun ColumnScope.LoginForm(
    loginDetails: LoginDetails,
    updateLoginDetails: (LoginDetails) -> Unit,
    validateForm: () -> Unit
) {
    EmailField(
        value = loginDetails.email,
        updateValue = { updateLoginDetails(loginDetails.copy(email = it)) }
    )
    PasswordField(
        value = loginDetails.password,
        imeAction = ImeAction.Done,
        updateValue = { updateLoginDetails(loginDetails.copy(password = it)) }
    )
    AuthFormFooter(label = stringResource(Res.string.login), onClick = validateForm)
}


@Composable
fun ColumnScope.RegistrationForm(
    registrationDetails: RegistrationDetails,
    updateRegistrationDetails: (RegistrationDetails) -> Unit,
    validateForm: () -> Unit
) {
    NameField(
        value = registrationDetails.name,
        updateValue = { updateRegistrationDetails(registrationDetails.copy(name = it)) }
    )
    EmailField(
        value = registrationDetails.email,
        updateValue = { updateRegistrationDetails(registrationDetails.copy(email = it)) }
    )
    PasswordField(
        value = registrationDetails.password,
        updateValue = { updateRegistrationDetails(registrationDetails.copy(password = it)) }
    )
    PasswordField(
        value = registrationDetails.confirmationPassword,
        imeAction = ImeAction.Done,
        confirmField = true,
        updateValue = { updateRegistrationDetails(registrationDetails.copy(confirmationPassword = it)) }
    )
    AuthFormFooter(
        label = stringResource(Res.string.register),
        onClick = validateForm
    )
}


@Composable
fun AuthHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Colors.TurboGreen)
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.plant_icon),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color.White
        )
        Text(
            stringResource(Res.string.welcome_turbo_plant),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Divider(thickness = 5.dp, color = Color.Transparent)
        Text(
            stringResource(Res.string.welcome_sub_title)
        )
    }
}

@Composable
fun SignModeToggle(currentSignMode: SignMode, updateSignMode: (SignMode) -> Unit) {
    Row(Modifier.padding(15.dp)) {
        SignMode.entries.forEach {
            val isSelected = it == currentSignMode
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSelected) Colors.TurboGreen else Colors.BlackGround
                ),
                onClick = { updateSignMode(it) }) {
                Text(
                    text = stringResource(it.labelRes),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}

@Composable
fun AuthFormFooter(
    label: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = Colors.TurboGreen, shape = RoundedCornerShape(5.dp))
                .height(50.dp)
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Text(
                text = label.uppercase(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
package fr.onat.turboplant.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.LocalNavigate
import fr.onat.turboplant.resources.Colors
import fr.onat.turboplant.viewModels.LoginViewModel
import fr.onat.turboplant.viewModels.NavigationRoute
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.email
import turboplant.composeapp.generated.resources.enter_your_password
import turboplant.composeapp.generated.resources.login
import turboplant.composeapp.generated.resources.no_account_register
import turboplant.composeapp.generated.resources.password

@OptIn(KoinExperimentalAPI::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.LightGray.copy(0.2f))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(20.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.login),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Colors.VerdiGreen,
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
            )
            LoginField(
                fieldName = stringResource(Res.string.email),
                value = email,
                updateValue = viewModel::updateEmail
            )
            LoginField(
                fieldName = stringResource(Res.string.password),
                value = password,
                updateValue = viewModel::updatePassword
            )
            LoginFooter(viewModel::sendLoginRequest)
        }
    }
}

@Composable
fun LoginField(
    fieldName: String,
    value: String,
    updateValue: (String) -> Unit
) {
    Column {
        Text(
            text = "$fieldName:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = value,
            onValueChange = updateValue,
            placeholder = {
                Text(stringResource(Res.string.enter_your_password))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LoginFooter(
    sendLoginRequest: ((Boolean) -> Unit) -> Unit
) {
    val navigate = LocalNavigate.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .background(Colors.VerdiGreen)
                .clickable {
                    sendLoginRequest { response ->
                        if (!response) return@sendLoginRequest
                        navigate(NavigationRoute.PLANT_LIST)
                    }
                }
        ) {
            Text(
                text = stringResource(Res.string.login).uppercase(),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp)
            )
        }

        Text(
            text = stringResource(Res.string.no_account_register),
            color = Colors.VerdiGreen,
        )
    }
}
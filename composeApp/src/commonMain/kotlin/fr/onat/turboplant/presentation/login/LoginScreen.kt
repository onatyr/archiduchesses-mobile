package fr.onat.turboplant.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.libs.extensions.collectAsEffect
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.login
import turboplant.composeapp.generated.resources.no_account_register
import turboplant.composeapp.generated.resources.plant_icon

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navigate: () -> Unit
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    viewModel.isAuthenticated.collectAsEffect {
        if (it) navigate()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Colors.TurboGreen)
            .fillMaxSize()
    ) {
        AppHeader(modifier = Modifier.padding(vertical = 90.dp))
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(20.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginField(
                loginFieldParams = LoginFieldParams.Email,
                value = email,
                updateValue = viewModel::updateEmail
            )
            LoginField(
                loginFieldParams = LoginFieldParams.Password,
                value = password,
                updateValue = viewModel::updatePassword
            )
            LoginFooter(viewModel::sendLoginRequest)
        }
    }
}

@Composable
fun AppHeader(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(Res.drawable.plant_icon),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color.White
        )
        Text(
            "Welcome to Turbo Plant",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
    }

}

@Composable
fun LoginField(
    loginFieldParams: LoginFieldParams,
    value: String,
    updateValue: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(loginFieldParams.label),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = value,
            onValueChange = updateValue,
            visualTransformation = loginFieldParams.visualTransformation,
            keyboardOptions = loginFieldParams.keyboardOptions,
            colors = TextFieldDefaults.textFieldColors(
                textColor = LocalContentColor.current,
                focusedIndicatorColor = Colors.SalmonPink
            ),
            placeholder = {
                Text(stringResource(loginFieldParams.placeHolder), color = Color.White)
            },
            modifier = Modifier.fillMaxWidth()
                .border(
                    2.dp,
                    LocalContentColor.current.copy(LocalContentAlpha.current),
                    RoundedCornerShape(5.dp)
                )
        )
    }
}

@Composable
fun LoginFooter(
    sendLoginRequest: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = Colors.TurboGreen, shape = RoundedCornerShape(5.dp))
                .height(40.dp)
                .fillMaxWidth()
                .clickable { sendLoginRequest() }
        ) {
            Text(
                text = stringResource(Res.string.login).uppercase(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp)
            )
        }
        Divider(thickness = 8.dp)
        Text(
            text = stringResource(Res.string.no_account_register),
            color = Colors.SalmonPink,
            fontWeight = FontWeight.Bold
        )
    }
}
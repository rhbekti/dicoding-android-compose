package com.rhbekti.stikacompose.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rhbekti.stikacompose.R
import com.rhbekti.stikacompose.ui.theme.StikaComposeTheme

@Composable
fun AboutScreen(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Header(
            navigateToBack = navigateToBack
        )
        Content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = modifier.clickable { navigateToBack() }
            )
        },
        title = {
            Text(stringResource(R.string.about))
        }
    )
}

@Composable
fun Content(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.profil),
            contentDescription = "my_profile",
            modifier = modifier
                .padding(8.dp)
                .size(100.dp)
        )
        Text(
            text = stringResource(R.string.name),
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(vertical = 8.dp)
        )
        Text(
            text = stringResource(R.string.email),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    StikaComposeTheme {
        AboutScreen(
            navigateToBack = {}
        )
    }
}
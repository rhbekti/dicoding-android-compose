package com.rhbekti.stikacompose.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rhbekti.stikacompose.R
import com.rhbekti.stikacompose.data.local.entity.UserEntity
import com.rhbekti.stikacompose.di.Injection
import com.rhbekti.stikacompose.model.User
import com.rhbekti.stikacompose.ui.ViewModelFactory
import com.rhbekti.stikacompose.ui.components.UserListItem
import com.rhbekti.stikacompose.ui.theme.StikaComposeTheme

@Composable
fun FavoriteScreen(
    navigateOnBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideUserRepository(
                LocalContext.current
            )
        )
    )
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Header(
            navigateOnBack = navigateOnBack
        )
        viewModel.userFavorites.collectAsState().value.let {
            Content(favorites = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    navigateOnBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(id = R.string.favorite))
        },
        navigationIcon = {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = modifier.clickable {
                    navigateOnBack()
                }
            )
        },
        modifier = modifier.background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun Content(
    favorites: List<UserEntity>,
    modifier: Modifier = Modifier
) {
    if (favorites.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.padding(top = 8.dp)
        ) {
            items(favorites) {
                UserListItem(
                    user = User(
                        it.login,
                        it.login,
                        it.avatarUrl
                    )
                )
            }
        }
    } else {
        Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
            Text(stringResource(id = R.string.no_favorite_user))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    StikaComposeTheme {
        FavoriteScreen(
            navigateOnBack = {}
        )
    }
}
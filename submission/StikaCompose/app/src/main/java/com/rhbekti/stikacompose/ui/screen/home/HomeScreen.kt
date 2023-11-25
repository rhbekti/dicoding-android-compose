package com.rhbekti.stikacompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rhbekti.stikacompose.R
import com.rhbekti.stikacompose.data.Result
import com.rhbekti.stikacompose.data.remote.response.UserItem
import com.rhbekti.stikacompose.di.Injection
import com.rhbekti.stikacompose.model.User
import com.rhbekti.stikacompose.ui.ViewModelFactory
import com.rhbekti.stikacompose.ui.components.LoadCardItem
import com.rhbekti.stikacompose.ui.components.SearchTopBar
import com.rhbekti.stikacompose.ui.components.UserListItem
import com.rhbekti.stikacompose.ui.theme.StikaComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideUserRepository(
                LocalContext.current
            )
        )
    ),
    navigateToDetail: (String) -> Unit,
    navigateToAbout: () -> Unit,
    navigateToFavorite: () -> Unit
) {
    val query by viewModel.query.collectAsState()

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        TopAppBar(
            title = { Text(stringResource(R.string.app_name)) },
            actions = {
                IconButton(
                    onClick = {
                        navigateToFavorite()
                    }
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {
                        navigateToAbout()
                    }
                ) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null
                    )
                }
            }
        )

        SearchTopBar(
            query = query,
            onQueryChange = viewModel::searchUserByUsername,
            modifier = modifier.padding(bottom = 16.dp)
        )

        viewModel.userList.collectAsState(initial = Result.Loading).value.let { userList ->
            when (userList) {
                is Result.Loading -> {
                    LoadCardItem()
                }

                is Result.Error -> {
                    ErrorContent()
                }

                is Result.Success -> {
                    HomeContent(
                        userList.data,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(id = R.string.empty_data))
    }
}

@Composable
fun HomeContent(
    userList: List<UserItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    if (userList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {

            items(userList) {
                UserListItem(
                    user = User(
                        it.id.toString(),
                        it.login.toString(),
                        it.avatarUrl.toString()
                    ),
                    modifier = Modifier.clickable {
                        navigateToDetail(it.login.toString())
                    }
                )
            }
        }
    } else {
        ErrorContent()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    StikaComposeTheme {
        HomeScreen(
            navigateToDetail = {},
            navigateToAbout = {},
            navigateToFavorite = {}
        )
    }
}
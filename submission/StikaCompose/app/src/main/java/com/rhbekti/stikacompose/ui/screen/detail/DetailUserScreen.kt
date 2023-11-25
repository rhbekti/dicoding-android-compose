package com.rhbekti.stikacompose.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.rhbekti.stikacompose.R
import com.rhbekti.stikacompose.data.Result
import com.rhbekti.stikacompose.data.local.entity.UserEntity
import com.rhbekti.stikacompose.data.remote.response.DetailUserResponse
import com.rhbekti.stikacompose.data.remote.response.UserRepoResponseItem
import com.rhbekti.stikacompose.di.Injection
import com.rhbekti.stikacompose.model.Repo
import com.rhbekti.stikacompose.ui.ViewModelFactory
import com.rhbekti.stikacompose.ui.components.RepoListItem

@Composable
fun DetailUserScreen(
    username: String,
    navigateOnBack: () -> Unit,
    viewModel: DetailUserViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideUserRepository(
                LocalContext.current
            )
        )
    )
) {
    LaunchedEffect(username) {
        viewModel.getDetailUserByUsername(username)
        viewModel.getUserReposByUsername(username)
    }

    val userDetailResult by viewModel.userData.collectAsState(initial = Result.Loading)
    val userReposResult by viewModel.userRepos.collectAsState(initial = Result.Loading)

    val isFavorite by viewModel.isFavoriteUser(username).collectAsState(initial = false)

    when {
        userDetailResult is Result.Loading || userReposResult is Result.Loading -> {
            LoadingContent()
        }

        userDetailResult is Result.Error || userReposResult is Result.Error -> {
            ErrorContent()
        }

        userDetailResult is Result.Success && userReposResult is Result.Success -> {
            val detailResult = userDetailResult as Result.Success<DetailUserResponse>
            val reposResult = userReposResult as Result.Success<List<UserRepoResponseItem>>

            val userEntity = UserEntity(
                detailResult.data.login.toString(),
                detailResult.data.avatarUrl.toString(),
                isFavorite
            )

            DetailUserContent(
                detailResult.data,
                userEntity,
                reposResult.data,
                navigateOnBack = navigateOnBack,
                onUserAsFavorite = { user ->
                    if (user.isFavorite) {
                        viewModel.deleteFromFavorite(user)
                    } else {
                        user.isFavorite = true
                        viewModel.saveUserAsFavorite(user)
                    }
                }
            )
        }
    }
}

@Composable
fun LoadingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            Icons.Default.CloudDownload,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Text(stringResource(id = R.string.load_data))
    }
}

@Composable
fun ErrorContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            Icons.Default.Error,
            contentDescription = null
        )
        Text(stringResource(id = R.string.empty_data))
    }
}

@Composable
fun DetailUserContent(
    user: DetailUserResponse,
    userEntity: UserEntity,
    data: List<UserRepoResponseItem>,
    navigateOnBack: () -> Unit,
    onUserAsFavorite: (UserEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Header(navigateOnBack = navigateOnBack)
        ProfileUser(user = user, userEntity = userEntity, onUserAsFavorite = onUserAsFavorite)
        ListRepository(data = data)
    }
}

@Composable
fun ProfileUser(
    user: DetailUserResponse,
    userEntity: UserEntity,
    onUserAsFavorite: (UserEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = user.name,
            modifier = modifier
                .padding(8.dp)
                .size(94.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            user.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                user.followers?.let { Text(text = stringResource(R.string.follower, it)) }
                user.following?.let { Text(text = stringResource(R.string.following, it)) }
            }
            if (userEntity.isFavorite) {
                Button(
                    onClick = {
                        onUserAsFavorite(userEntity)
                    },
                    modifier = modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null
                    )
                    Spacer(modifier = modifier.size(ButtonDefaults.IconSize))
                    Text(stringResource(id = R.string.favorite))
                }
            } else {
                Button(
                    onClick = {
                        onUserAsFavorite(userEntity)
                    },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    modifier = modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = null
                    )
                    Spacer(modifier = modifier.size(ButtonDefaults.IconSize))
                    Text(stringResource(id = R.string.favorite))
                }
            }
        }
    }
}

@Composable
fun ListRepository(
    data: List<UserRepoResponseItem>,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Repository :",
        fontWeight = FontWeight.Bold
    )
    Divider(
        thickness = 1.dp,
        color = Color.LightGray,
        modifier = modifier.padding(vertical = 8.dp)
    )
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(data) {
            RepoListItem(
                repo = Repo(
                    it.id.toString(),
                    it.name ?: "-",
                    it.language ?: "-"
                )
            )
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
        navigationIcon = {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = modifier.clickable { navigateOnBack() }
            )
        },
        title = {
            Text(
                stringResource(R.string.detail_user)
            )
        }
    )
}


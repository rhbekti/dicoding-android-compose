package com.rhbekti.stikacompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rhbekti.stikacompose.model.Repo
import com.rhbekti.stikacompose.model.User
import com.rhbekti.stikacompose.ui.theme.StikaComposeTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun UserListItem(
    user: User,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(8.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Text(
            text = user.name,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LoadCardItem(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .padding(8.dp)
                .size(64.dp)
                .shimmer()
                .background(Color.LightGray)
                .clip(CircleShape)
        )
        Box(
            modifier = modifier
                .padding(8.dp)
                .shimmer()
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(16.dp)
        )
    }
}


@Composable
fun RepoListItem(
    repo: Repo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            vertical =
            8.dp
        )
    ) {
        Text(
            text = repo.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier
                    .size(16.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = modifier.padding(horizontal = 8.dp))
            Text(repo.language)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardItemPreview() {
    StikaComposeTheme {
        UserListItem(
            User(
                "10",
                "DR. Sutomo",
                "https://raw.githubusercontent.com/dicodingacademy/assets/main/android_compose_academy/pahlawan/10.jpg"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadCardItemPreview() {
    StikaComposeTheme {
        LoadCardItem()
    }
}

@Preview(showBackground = true)
@Composable
fun RepoCardItemPreview() {
    StikaComposeTheme {
        RepoListItem(
            repo = Repo(
                "123",
                "Testing",
                "Java"
            )
        )
    }
}
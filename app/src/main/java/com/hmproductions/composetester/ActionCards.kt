package com.hmproductions.composetester

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmproductions.composetester.ui.theme.ComposeTesterTheme
import com.hmproductions.composetester.ui.theme.Purple500

@Composable
fun FavoritesCard() {
    Card(
        backgroundColor = Purple500,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp, 0.dp),
                text = stringResource(id = R.string.add_to_favorites),
                style = MaterialTheme.typography.h6,
                color = Color.White
            )

            Image(
                painter = painterResource(id = R.drawable.heart_white_icon),
                contentDescription = stringResource(id = R.string.add_to_favorites)
            )

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun DeleteCard() {
    Card(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.Red) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = stringResource(id = R.string.delete_favorite)
            )

            Text(
                modifier = Modifier.padding(16.dp, 0.dp),
                text = stringResource(id = R.string.delete_favorite),
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewFavoritesCard() {
    ComposeTesterTheme {
        FavoritesCard()
    }
}
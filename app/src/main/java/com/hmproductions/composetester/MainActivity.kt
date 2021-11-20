package com.hmproductions.composetester

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmproductions.composetester.ui.theme.ComposeTesterTheme
import com.hmproductions.swipeableactioncard.SwipeableActionCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTesterTheme {
                MyScreen()
            }
        }
    }
}

@Composable
fun MyScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
            SwipeableActionCard({ MainCard() }, { FavoritesCard() }, { DeleteCard() }, {
                Toast.makeText(context, "Swiped left", Toast.LENGTH_SHORT).show()
                Log.v("SwipeableActionCard", "swiped left")
            }, {
                Toast.makeText(context, "Swiped right", Toast.LENGTH_SHORT).show()
                Log.v("SwipeableActionCard", "swiped right")
            })
        }
    }
}

@Composable
fun MainCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.landscape),
                    contentDescription = stringResource(id = R.string.app_name),
                    Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = "Albert Einstein", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Known for The Theory of Relativity",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTesterTheme {
        MyScreen()
    }
}
package dev.motivateme.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.motivateme.R
import dev.motivateme.data.sampleData
import dev.motivateme.models.Topic
import dev.motivateme.ui.components.TopAppBarTitle
import dev.motivateme.ui.theme.MotivateMeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicScreen(
    topics: List<Topic>,
    onTopicClick: (topicName: String) -> Unit,
    welcomeText: String = stringResource(R.string.welcome_text),
    loading: Boolean = true,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { TopAppBarTitle(stringResource(R.string.app_name)) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                WelcomeText(welcomeText)
            }
            items(topics) { topic ->
                TopicBlock(
                    topic = topic,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onTopicClick.invoke(topic.name)
                        }
                )
            }
            if (loading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }
        }
    }
}

@Composable
fun WelcomeText(welcomeText: String, modifier: Modifier = Modifier) {
    Text(
        text = welcomeText,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
    )
}

@Composable
fun TopicBlock(
    topic: Topic,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            disabledContentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
//            if (topic.isGenerative) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_sparkle),
//                    contentDescription = "sparkle icon",
//                    modifier = Modifier.padding(end = 16.dp)
//                )
//            }
            Text(
                text = topic.name.capitalize(Locale.current),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicScreenPreview() {
    MotivateMeTheme {
        TopicScreen(
            topics = sampleData,
            onTopicClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeTextPreview() {
    MotivateMeTheme {
        WelcomeText(stringResource(R.string.welcome_text))
    }
}

@Preview(showBackground = true)
@Composable
fun TopicBlockPreview() {
    MotivateMeTheme {
        TopicBlock(sampleData.first())
    }
}
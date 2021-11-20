package com.hmproductions.swipeableactioncard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private enum class SwipeCardState {
    DEFAULT,
    LEFT,
    RIGHT
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SwipeableActionCard(
    mainCard: @Composable () -> Unit, leftSwipeCard: @Composable () -> Unit,
    rightSwipeCard: @Composable () -> Unit, leftSwiped: () -> Unit, rightSwiped: () -> Unit,
    modifier: Modifier = Modifier, animationSpec: AnimationSpec<Float> = tween(250)
) {
    ConstraintLayout(modifier = modifier) {
        val (mainCardRef, actionCardRef) = createRefs()
        val swipeableState = rememberSwipeableState(
            initialValue = SwipeCardState.DEFAULT,
            animationSpec = animationSpec
        )
        val coroutineScope = rememberCoroutineScope()

        /* Tracks if left or right action card to be shown */
        val swipeLeftCardVisible = remember { mutableStateOf(false) }

        /* Disable swipe when card is animating back to default position */
        val swipeEnabled = remember { mutableStateOf(true) }

        val maxWidthInPx = with(LocalDensity.current) {
            LocalConfiguration.current.screenWidthDp.dp.toPx()
        }
        val anchors = mapOf(
            0f to SwipeCardState.DEFAULT,
            -maxWidthInPx to SwipeCardState.LEFT,
            maxWidthInPx to SwipeCardState.RIGHT
        )

        /* This surface is for action card which is below the main card */
        Surface(
            color = Color.Transparent,
            content = if (swipeLeftCardVisible.value) leftSwipeCard else rightSwipeCard,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(actionCardRef) {
                    top.linkTo(mainCardRef.top)
                    bottom.linkTo(mainCardRef.bottom)
                    height = Dimension.fillToConstraints
                }
        )

        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Horizontal,
                    enabled = swipeEnabled.value,
                    thresholds = { _, _ -> FractionalThreshold(0.6f) },
                    velocityThreshold = 125.dp
                )
                .constrainAs(mainCardRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) {

            if (swipeableState.currentValue == SwipeCardState.LEFT && !swipeableState.isAnimationRunning) {
                leftSwiped()
                coroutineScope.launch {
                    swipeEnabled.value = false
                    swipeableState.animateTo(SwipeCardState.DEFAULT)
                    swipeEnabled.value = true
                }
            } else if (swipeableState.currentValue == SwipeCardState.RIGHT && !swipeableState.isAnimationRunning) {
                rightSwiped()
                coroutineScope.launch {
                    swipeEnabled.value = false
                    swipeableState.animateTo(SwipeCardState.DEFAULT)
                    swipeEnabled.value = true
                }
            }

            swipeLeftCardVisible.value = swipeableState.offset.value <= 0

            mainCard()
        }
    }
}
package nl.coffeeit.aroma.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import nl.coffeeit.aroma.DEFAULT_BACKGROUND_COLOR
import nl.coffeeit.aroma.DEFAULT_CORNER_RADIUS
import nl.coffeeit.aroma.DEFAULT_SCRIM_COLOR

@Composable
@ExperimentalMaterialApi
fun BottomSheetWithContent(
    activityContentScope: @Composable (state: ModalBottomSheetState, scope: CoroutineScope) -> Unit,
    sheetContent: @Composable () -> Unit,
    backgroundColor: Color = Color(DEFAULT_BACKGROUND_COLOR),
    scrimColor: Color = Color(DEFAULT_SCRIM_COLOR),
    width: Float? = null,
    isDraggable : Boolean = true,
    bottomPadding: Float = 0f,
    cornerShape: RoundedCornerShape = RoundedCornerShape(DEFAULT_CORNER_RADIUS.dp),
    accessory: Accessory = Accessory.NONE
) {

    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    val sheetSurfaceModifier = if (width != null && width > 0) {
        Modifier.width(width.dp)
    } else {
        Modifier.fillMaxWidth()
    }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = if (bottomPadding != 0f) {
            RoundedCornerShape(
                cornerShape.topStart,
                cornerShape.topEnd,
                cornerShape.bottomStart,
                cornerShape.bottomEnd
            )
        } else {
            RoundedCornerShape(
                cornerShape.topStart,
                cornerShape.topEnd,
                CornerSize(0.dp),
                CornerSize(0.dp)
            )
        },
        scrimColor = scrimColor,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Surface(
                modifier = sheetSurfaceModifier
                    .align(CenterHorizontally)
                    .padding(bottom = bottomPadding.dp),
                color = backgroundColor
            ) {

                Column {
                    when (accessory) {
                        Accessory.GRABBER -> {
                            Grabber(
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 40.dp)
                                    .align(CenterHorizontally)
                            )
                        }
                        Accessory.CLOSE_BUTTON -> {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .align(Alignment.End)
                                    .padding(16.dp)
                                    .clickable {
                                        scope.launch { state.hide() }
                                    },
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "Close button"
                                )
                            }
                        }
                        Accessory.NONE -> {}
                    }
                    sheetContent()
                }
            }
        }
    ) {
        activityContentScope(state, scope)
    }
}

@Composable
private fun Grabber(modifier: Modifier) {
    Column(modifier = modifier
        .height(4.dp)
        .width(45.dp)
        .wrapContentSize()) {
        Box(
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFB4B4B4))
        )
    }
}

enum class Accessory {
    NONE, GRABBER, CLOSE_BUTTON
}


/*
 * Created by Coffee IT
 *
 * MIT License
 *
 * Copyright (c) 2022 Coffee IT
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package nl.coffeeit.aroma.bottomsheet

import androidx.compose.foundation.Image
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

private const val DEFAULT_BACKGROUND_COLOR = 0xFFF6F6F6
private const val DEFAULT_CORNER_RADIUS = 16
private const val DEFAULT_SCRIM_COLOR = 0x99000000

@Composable
@ExperimentalMaterialApi
fun BottomSheetWithContent(
    activityContentScope: @Composable (state: ModalBottomSheetState, scope: CoroutineScope) -> Unit,
    sheetContent: @Composable () -> Unit,
    backgroundColor: Color = Color(DEFAULT_BACKGROUND_COLOR),
    scrimColor: Color = Color(DEFAULT_SCRIM_COLOR),
    width: Float? = null,
    bottomMargin: Float = 0f,
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
        scrimColor = scrimColor,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Surface(
                modifier = sheetSurfaceModifier
                    .align(CenterHorizontally)
                    .padding(bottom = bottomMargin.dp),
                color = backgroundColor,
                shape = if (bottomMargin != 0f) {
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
                }
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
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(100.dp))
                                        .clickable {
                                            scope.launch { state.hide() }
                                        }
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.is_close_bottomsheet),
                                        contentDescription = accessory.displayName
                                    )
                                }
                            }
                        }
                        Accessory.NONE -> {
                            // Nothing needs to happen
                        }
                    }
                    sheetContent()
                }
            }
        }
    ) {
        activityContentScope(state, scope)
    }
}


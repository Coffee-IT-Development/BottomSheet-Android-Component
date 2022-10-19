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

package nl.coffeeit.aroma.bottomsheet.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ajalt.colormath.model.HSV
import com.github.ajalt.colormath.model.RGB
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import kotlinx.coroutines.launch
import nl.coffeeit.aroma.bottomsheet.Accessory
import nl.coffeeit.aroma.bottomsheet.BottomSheetWithContent
import java.math.BigDecimal
import kotlin.math.roundToInt

const val DEFAULT_CORNER_RADIUS = 16f
const val DEFAULT_SCRIM_COLOR = 0x99B0B0B3
const val DEFAULT_SCRIM_COLOR_ALPHA = 0.7
const val DEFAULT_SCRIM_COLOR_BLUE = 176
const val DEFAULT_SCRIM_COLOR_GREEN = 176
const val DEFAULT_SCRIM_COLOR_RED = 153

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComponentsDemoScreen()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ComponentsDemoScreen(
) {
    var bottomSheetAccessory by remember { mutableStateOf(Accessory.NONE) }
    var bottomSheetBottomMargin by remember { mutableStateOf(0f) }
    var bottomSheetRgbColor by remember {
        mutableStateOf(
            RGB(
                DEFAULT_SCRIM_COLOR_RED,
                DEFAULT_SCRIM_COLOR_GREEN,
                DEFAULT_SCRIM_COLOR_BLUE,
                DEFAULT_SCRIM_COLOR_ALPHA
            )
        )
    }
    var bottomSheetRoundedCornerSize by remember { mutableStateOf(DEFAULT_CORNER_RADIUS) }
    var bottomSheetScrimColor by remember { mutableStateOf(Color(DEFAULT_SCRIM_COLOR)) }
    var bottomSheetScrimColorAlpha by remember { mutableStateOf(DEFAULT_SCRIM_COLOR_ALPHA) }
    var bottomSheetWidth by remember { mutableStateOf(0f) }

    val accessoryItems = listOf(Accessory.NONE, Accessory.GRABBER, Accessory.CLOSE_BUTTON)
    val selectedAccessory = remember { mutableStateOf(Accessory.NONE) }
    val isAccessorySelected: (Accessory) -> Boolean = { selectedAccessory.value == it }
    val onAccessoryStateChange: (Accessory) -> Unit = { selectedAccessory.value = it }

    BottomSheetWithContent(
        { state, scope ->
            Column {
                Spacer(modifier = Modifier.height(48.dp))

                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {

                        Column {
                            Text(text = "Corner radius: ${bottomSheetRoundedCornerSize.toInt()}dp")

                            Slider(value = bottomSheetRoundedCornerSize, onValueChange = { value ->
                                val roundedValue = value.roundToInt().toFloat()
                                bottomSheetRoundedCornerSize = roundedValue
                            }, valueRange = 0f..72f)

                            Spacer(modifier = Modifier.width(32.dp))

                            Text(text = "Width: ${bottomSheetWidth.toInt()}dp (enter 0dp for full width)")

                            Slider(value = bottomSheetWidth, onValueChange = { value ->
                                val roundedValue = value.roundToInt().toFloat()
                                bottomSheetWidth = roundedValue
                            }, valueRange = 0f..250f)

                            Spacer(modifier = Modifier.width(32.dp))

                            Text(text = "Bottom margin: ${bottomSheetBottomMargin.toInt()}dp")

                            Slider(value = bottomSheetBottomMargin, onValueChange = { value ->
                                val roundedValue = value.roundToInt().toFloat()
                                bottomSheetBottomMargin = roundedValue
                            }, valueRange = 0f..250f)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column {
                            Text(
                                text = "Accessory:"
                            )

                            Row(
                                Modifier
                                    .wrapContentSize()
                                    .padding(top = 8.dp)
                            ) {
                                accessoryItems.forEach { item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .selectable(
                                                selected = isAccessorySelected(item),
                                                onClick = {
                                                    onAccessoryStateChange(item)
                                                    bottomSheetAccessory = item
                                                },
                                                role = Role.RadioButton
                                            )
                                            .padding(end = 4.dp)
                                            .wrapContentSize()
                                    ) {
                                        RadioButton(
                                            selected = isAccessorySelected(item),
                                            onClick = null
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))

                                        Text(
                                            text = item.displayName
                                        )
                                    }
                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Scrim alpha: $bottomSheetScrimColorAlpha")

                        Slider(
                            value = bottomSheetScrimColorAlpha.toFloat(),
                            onValueChange = { value ->
                                var valueFormatted = BigDecimal(value.toDouble())
                                valueFormatted = valueFormatted.setScale(2, BigDecimal.ROUND_UP)
                                bottomSheetScrimColorAlpha = valueFormatted.toDouble()
                                bottomSheetRgbColor = RGB(
                                    bottomSheetRgbColor.r,
                                    bottomSheetRgbColor.g,
                                    bottomSheetRgbColor.b,
                                    bottomSheetScrimColorAlpha
                                )
                                bottomSheetScrimColor = Color(
                                    bottomSheetRgbColor.redInt,
                                    bottomSheetRgbColor.greenInt,
                                    bottomSheetRgbColor.blueInt,
                                    bottomSheetRgbColor.alphaInt
                                )
                            },
                            valueRange = 0f..1f
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Scrim color and brightness:")

                        HarmonyColorPicker(
                            harmonyMode = ColorHarmonyMode.SHADES,
                            modifier = Modifier
                                .size(275.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color(DEFAULT_SCRIM_COLOR),
                            onColorChanged = { hsvColor ->
                                bottomSheetRgbColor = HSV(
                                    hsvColor.hue,
                                    hsvColor.saturation,
                                    hsvColor.value,
                                    bottomSheetScrimColorAlpha
                                ).toSRGB()
                                bottomSheetScrimColor = Color(
                                    bottomSheetRgbColor.redInt,
                                    bottomSheetRgbColor.greenInt,
                                    bottomSheetRgbColor.blueInt,
                                    bottomSheetRgbColor.alphaInt
                                )
                            })

                        Button(modifier = Modifier
                            .fillMaxWidth(),
                            onClick = {
                                scope.launch {
                                    state.show()
                                }
                            }
                        ) {
                            Text(text = "Show BottomSheet")
                        }
                    }
                }

                BackHandler(
                    enabled = (state.currentValue != ModalBottomSheetValue.Hidden),
                    onBack = {
                        scope.launch { state.hide() }
                    }
                )
            }
        },
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {

                Image(
                    modifier = Modifier
                        .width(231.dp)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 72.dp),
                    painter = painterResource(R.drawable.ill_aroma_logo),
                    contentDescription = "Coffee IT's Aroma logo",
                    contentScale = ContentScale.FillWidth
                )
            }
        },
        width = bottomSheetWidth,
        bottomMargin = bottomSheetBottomMargin,
        accessory = bottomSheetAccessory,
        cornerShape = RoundedCornerShape(bottomSheetRoundedCornerSize.dp),
        scrimColor = bottomSheetScrimColor
    )
}


@Composable
@Preview(showBackground = true)
fun ModalBottomSheetHolderPreview() {
    ComponentsDemoScreen()
}
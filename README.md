# Android Aroma BottomSheet

This document describes how to implement the Aroma BottomSheet component into an app

# Installation

This component requires minimum SDK 21.

Add the following to `build.gradle`:
```
dependencies {
    implementation "nl.coffeeit.aroma:bottomsheet:1.0.0"
}
```

# Usage

To use the component as a Composable, simply add it to your code as:

```
BottomSheetWithContent(
        activityContentScope = { state, scope ->
            Button(onClick = {
                scope.launch {
                    state.show()
                }
            }) {
                Text("This is behind the sheet")
            }
        },
        
        sheetContent = {
            Text(text = "This is inside the sheet")
        }
    )
```
Two parameters are required:

`activityContentScope` holds the content that should be displayed initially, when the bottom sheet is hidden. It offers a `state` to toggle the visibility of the bottom sheet and a coroutine `scope`

`sheetContent` represents a Composable that is the the content of the actual sheet

## Customisation
`BottomSheetWithContent` accepts the following optional parameters:

- `backgroundColor` A `Color` object to set the background of the bottom sheet
- `scrimColor` A `Color` object to set the color of the area behind the sheet
- `width` The width of the bottom sheet (default is fill screen width)
- `bottomPadding` Padding for the bottom of the sheet, to make a floating variant (default 0)
- `cornerShape` A `RoundedCornerShape` object to give the sheet rounded edges
- `Accessory` Decides the accessory of the bottomsheet:
  -- `NONE`
  -- `GRABBER`
  -- `CLOSE_BUTTON`


# Change log

Version 1.0.0 *(01-07-2022)*
----------------------------
* Added BottomSheet component
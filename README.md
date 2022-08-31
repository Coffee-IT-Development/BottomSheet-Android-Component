[![Coffee IT - Android Aroma BottomSheet Component](https://coffeeit.nl/wp-content/uploads/2022/08/Aroma-emoji-picker-cover-android.png)](https://coffeeit.nl/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/nl.coffeeit.aroma/bottomsheet/badge.svg)](https://maven-badges.herokuapp.com/Maven-Central/nl.coffeeit.aroma/bottomsheet)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/Coffee-IT-Development/BottomSheet-Android-Component/blob/main/LICENSE)
[![Mirror Repository](https://img.shields.io/badge/Mirror-Repository-9b34eb?style=flat-square)](https://github.com/Coffee-IT-Development/BottomSheet-Android-Component)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-@CoffeeIT-blue.svg?style=flat-square)](https://linkedin.com/company/coffee-it)
[![Facebook](https://img.shields.io/badge/Facebook-CoffeeITNL-blue.svg?style=flat-square)](https://www.facebook.com/CoffeeITNL/)
[![Instagram](https://img.shields.io/badge/Instagram-CoffeeITNL-blue.svg?style=flat-square)](https://www.instagram.com/coffeeitnl/)
[![Follow coffeeitnl on Twitter](https://img.shields.io/twitter/follow/coffeeitnl.svg?style=flat-square&logo=twitter)](https://twitter.com/coffeeitnl)

# ☕️ Android Aroma BottomSheet

The Android Aroma BottomSheet package provides a customisable BottomSheet. This README describes how to implement the BottomSheet into an app.

This GitHub repository is a mirror, the official repository is hosted privately by Coffee IT.

Created by [Coffee IT](https://coffeeit.nl/).

<img src="docs/images/Demo.gif" width="300">

# ⚡ Installation

This component requires minimum SDK 21.

Add the following to `build.gradle`:
```
dependencies {
    implementation "nl.coffeeit.aroma:bottomsheet:1.0.1"
}
```

# 📖 Usage

To use the BottomSheet in a project, simply add it to your code like this:

```
BottomSheetWithContent(
        activityContentScope = { state, scope ->
            Button(onClick = {
                scope.launch {
                    state.show()
                }
            }) {
                Text("This goes behind the sheet")
            }
        },
        
        sheetContent = {
            Text(text = "This goes inside the sheet")
        }
    )
```
Two parameters are required:

`activityContentScope` holds the content that should be displayed initially, when the bottom sheet is hidden. It offers a `state` to toggle the visibility of the bottom sheet and a coroutine `scope`

`sheetContent` represents a Composable that is the the content of the actual sheet. XML can also be used here, since it can be inflated in a Composable.

# ⚙️ Customisation

`BottomSheetWithContent` accepts the following optional parameters:

- `backgroundColor` A `Color` object to set the background of the bottom sheet
- `scrimColor` A `Color` object to set the color of the area behind the sheet
- `width` The width of the bottom sheet (default is fill screen width)
- `bottomMargin` Margin for the bottom of the sheet, to make a floating variant (default 0)
- `cornerShape` A `RoundedCornerShape` object to give the sheet rounded edges
- `accessory` Decides the accessory of the BottomSheet, can be:
  -- `NONE`
  -- `GRABBER`
  -- `CLOSE_BUTTON`

# ✏️ Changelog

The changelog can be found [here](https://github.com/Coffee-IT-Development/BottomSheet-Android-Component/blob/main/CHANGELOG.md).

# 🔗 Related publications

Look at our other repositories on our [GitHub account](https://github.com/orgs/Coffee-IT-Development/repositories).

# 📧 Contact

Do you have questions, ideas or need help? Send us an email at contact@coffeeit.nl.

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://global-uploads.webflow.com/605a171ee93af49275331843/623b23cdea80a92703e61b42_Logo_black_1.svg" width="100">
  <source media="(prefers-color-scheme: light)" srcset="https://coffeeit.nl/wp-content/uploads/2016/09/logo_dark_small_new.png" width="100">
  <img alt="The Coffee IT logo" src="https://coffeeit.nl/wp-content/uploads/2016/09/logo_dark_small_new.png">
</picture>

# ⚠️ License

Android Aroma BottomSheet is licensed under the terms of the [MIT Open Source license](https://github.com/Coffee-IT-Development/BottomSheet-Android-Component/blob/main/LICENSE).
package me.smbduknow.vegandrinks.compose

import android.util.TypedValue
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.compose.Composable
import androidx.compose.Context
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialColors
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import me.smbduknow.vegandrinks.BuildConfig
import me.smbduknow.vegandrinks.R

@Composable
fun Hello(name: String, context: Context) = MaterialTheme(
    colors = MaterialColors(
        primary = resolveColor(context, R.attr.colorPrimary, MaterialColors().primary),
        secondary = resolveColor(context, R.attr.colorSecondary, MaterialColors().secondary),
        onBackground = resolveColor(context, R.attr.background, MaterialColors().onBackground)
    )
) {
    FlexColumn {
        inflexible {
            TopAppBar<MenuItem>(
                title = { Text("Jetpack Compose Sample") }
            )
        }
        expanded(1F) {
            Column {
                Column {
                    Title(context)
                    HeightSpacer(60.dp)
                    MyButton(context)
                }
                Row {
                    Title(context)
                    WidthSpacer(16.dp)
                    Title(context)
                }
            }
        }
    }
}



private fun Title(context: Context) {
    Center {
        Padding(16.dp) {
            Text(context.getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME)
        }
    }
}

private fun MyButton(context: Context) {
    Center {
        Padding(16.dp) {
            Button("Button", onClick = {
                Toast.makeText(context, "asasas", Toast.LENGTH_SHORT).show()
            })
        }
    }
}

private fun resolveColor(context: Context?, @AttrRes attrRes: Int, colorDefault: Color) =
    context?.let { Color(resolveThemeAttr(it, attrRes).data.toLong()) } ?: colorDefault

private fun resolveThemeAttr(context: Context, @AttrRes attrRes: Int): TypedValue {
    val theme = context.theme
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue
}

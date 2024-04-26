package com.paperloong.illuminometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.paperloong.illuminometer.ui.IlluminometerApp
import com.paperloong.illuminometer.ui.theme.IlluminanceTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/23
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IlluminanceTheme {
                IlluminometerApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    IlluminanceTheme {
        IlluminometerApp()
    }
}



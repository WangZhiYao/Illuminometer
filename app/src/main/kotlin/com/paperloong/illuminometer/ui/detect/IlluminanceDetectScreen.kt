package com.paperloong.illuminometer.ui.detect

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.paperloong.illuminometer.R
import com.paperloong.illuminometer.constant.IlluminanceUnit
import com.paperloong.illuminometer.ui.theme.IlluminanceTheme
import com.paperloong.illuminometer.ui.theme.seed
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/23
 */
@Composable
fun IlluminanceDetectScreen(
    viewModel: IlluminanceDetectViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    LifecycleResumeEffect(lifecycleOwner = LocalLifecycleOwner.current) {
        viewModel.registerLightSensorEventListener()
        onPauseOrDispose {
            viewModel.unregisterLightSensorEventListener()
        }
    }

    val scope = rememberCoroutineScope()
    val state by viewModel.collectAsState()
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is Snack -> {
                scope.launch {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
            }
        }
    }

    IlluminanceDetectContent(
        state,
        Modifier,
        snackbarHostState,
        onUnitClick = { unit -> viewModel.setIlluminanceUnit(unit) }
    )
}

@Composable
fun IlluminanceDetectContent(
    state: IlluminanceDetectUiState,
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    onUnitClick: (IlluminanceUnit) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (valueCard, illuminance, unitGroup) = createRefs()

            ValueCardCombination(
                state.min, state.avg, state.max,
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(valueCard) {
                        top.linkTo(parent.top, margin = 32.dp)
                    }
            )

            Illuminance(state.current, state.unit,
                modifier.constrainAs(illuminance) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )

            IlluminanceUnitGroup(
                onUnitClick,
                state.unit,
                modifier.constrainAs(unitGroup) {
                    top.linkTo(illuminance.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Composable
fun ValueCardCombination(
    min: String,
    avg: String,
    max: String,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ValueCard(
            title = stringResource(id = R.string.min),
            value = min,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
        )
        ValueCard(
            title = stringResource(id = R.string.avg),
            value = avg,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
        )
        ValueCard(
            title = stringResource(id = R.string.max),
            value = max,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
        )
    }
}

@Composable
fun ValueCard(title: String, value: String, modifier: Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = value,
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun Illuminance(value: String, unit: IlluminanceUnit?, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            maxLines = 1,
            style = MaterialTheme.typography.headlineLarge
        )
        val name = if (unit == null) {
            ""
        } else {
            "(${unit.name})"
        }
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun IlluminanceUnitGroup(
    onUnitClick: (IlluminanceUnit) -> Unit,
    unit: IlluminanceUnit?,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FilledTonalButton(
            onClick = { onUnitClick(IlluminanceUnit.LUX) },
            modifier = Modifier
                .width(128.dp)
                .height(48.dp),
            border = if (unit == IlluminanceUnit.LUX) BorderStroke(1.dp, seed) else null
        ) {
            Text(
                text = IlluminanceUnit.LUX.name,
                color = if (unit == IlluminanceUnit.LUX) seed else Color.Unspecified,
                style = MaterialTheme.typography.labelLarge
            )
        }
        FilledTonalButton(
            onClick = { onUnitClick(IlluminanceUnit.FC) },
            modifier = Modifier
                .width(128.dp)
                .height(48.dp),
            border = if (unit == IlluminanceUnit.FC) BorderStroke(1.dp, seed) else null
        ) {
            Text(
                text = IlluminanceUnit.FC.name,
                color = if (unit == IlluminanceUnit.FC) seed else Color.Unspecified,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun IlluminanceDetectScreenPreview() {
    IlluminanceTheme {
        IlluminanceDetectContent(
            IlluminanceDetectUiState(
                "100",
                "200",
                "300",
                "300",
                IlluminanceUnit.LUX
            ),
            Modifier,
            SnackbarHostState(),
            onUnitClick = {}
        )
    }
}

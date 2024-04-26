package com.paperloong.illuminometer.ui.detect

import android.app.Application
import android.hardware.Sensor
import androidx.lifecycle.AndroidViewModel
import com.paperloong.illuminometer.constant.Dispatcher
import com.paperloong.illuminometer.constant.IlluminanceUnit
import com.paperloong.illuminometer.constant.IlluminometerDispatcher
import com.paperloong.illuminometer.data.SettingRepository
import com.paperloong.illuminometer.ext.luxToFc
import com.paperloong.illuminometer.ext.sensorEventFlow
import com.paperloong.illuminometer.model.SensorEventRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/23
 */
@HiltViewModel
class IlluminanceDetectViewModel @Inject constructor(
    private val application: Application,
    private val settingRepository: SettingRepository,
    @Dispatcher(IlluminometerDispatcher.IO) private val dispatcher: CoroutineDispatcher
) : ContainerHost<IlluminanceDetectUiState, IlluminanceDetectSideEffect>,
    AndroidViewModel(application) {

    override val container: Container<IlluminanceDetectUiState, IlluminanceDetectSideEffect> =
        container(IlluminanceDetectUiState())

    private var currentJob: Job? = null
    private val sensorEventRecordList: MutableList<SensorEventRecord> = mutableListOf()

    init {
        intent {
            settingRepository.getIlluminanceUnit()
                .flowOn(dispatcher)
                .onEach { sensorEventRecordList.clear() }
                .collect { unit ->
                    reduce {
                        IlluminanceDetectUiState(unit = unit)
                    }
                }
        }
    }

    fun registerLightSensorEventListener() {
        currentJob = intent {
            sensorEventFlow(application, Sensor.TYPE_LIGHT)
                .map { sensorEvent ->
                    val value = sensorEvent.values[0]
                    when (state.unit) {
                        IlluminanceUnit.LUX -> value
                        IlluminanceUnit.FC -> value.luxToFc()
                        null -> value
                    }
                }
                .flowOn(dispatcher)
                .onEach { value ->
                    sensorEventRecordList.add(SensorEventRecord(value))
                }
                .collect { value ->
                    val min = if (state.min.isBlank()) value else min(state.min.toFloat(), value)
                    val avg = sensorEventRecordList.map { it.value }.average()
                    val max = if (state.max.isBlank()) value else max(state.max.toFloat(), value)
                    reduce {
                        state.copy(
                            min = min.roundToInt().toString(),
                            avg = avg.roundToInt().toString(),
                            max = max.roundToInt().toString(),
                            current = value.roundToInt().toString()
                        )
                    }
                }
        }
    }

    fun unregisterLightSensorEventListener() {
        currentJob?.let {
            it.cancel()
            currentJob = null
        }
    }

    fun setIlluminanceUnit(unit: IlluminanceUnit) {
        intent {
            settingRepository.setIlluminanceUnit(unit)
        }
    }
}
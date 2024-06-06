package com.example.luxmetr.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {

    private val _appName = MutableLiveData<String>().apply {
        value = "Lux Meter"
    }
    val appName: LiveData<String> = _appName

    private val _description = MutableLiveData<String>().apply {
        value = "Lux Meter — это приложение для измерения уровня освещенности с использованием встроенных сенсоров вашего устройства. Приложение позволяет пользователю мониторить и анализировать изменения уровня освещенности в режиме реального времени."
    }
    val description: LiveData<String> = _description

    private val _featuresTitle = MutableLiveData<String>().apply {
        value = "Основные функции:"
    }
    val featuresTitle: LiveData<String> = _featuresTitle

    private val _features = MutableLiveData<String>().apply {
        value = """
            - Измерение освещенности: Использует встроенный световой сенсор вашего устройства для измерения уровня освещенности в люксах (lx).
            - Реальное время: Показания обновляются в реальном времени, предоставляя точные и актуальные данные.
            - Графическое отображение: Данные освещенности отображаются на графике, что позволяет отслеживать изменения уровня освещенности за определенный период времени.
            - Исторические данные: Приложение сохраняет данные об освещенности, позволяя просматривать историю изменений.
        """.trimIndent()
    }
    val features: LiveData<String> = _features

    private val _interfaceTitle = MutableLiveData<String>().apply {
        value = "Описание интерфейса:"
    }
    val interfaceTitle: LiveData<String> = _interfaceTitle

    private val _interfaceDescription = MutableLiveData<String>().apply {
        value = """
            - Главный экран: На главном экране отображается текущий уровень освещенности и его статус (например, "Яркий" или "Тусклый").
            - Графический экран: График изменения уровня освещенности за последнюю 1 минуту, обновляемый каждые 100 мс.
            - Навигационная панель: Содержит иконки для быстрого доступа к основным функциям приложения. Включает в себя боковое меню с настройками и дополнительной информацией.
        """.trimIndent()
    }
    val interfaceDescription: LiveData<String> = _interfaceDescription

    private val _technicalDetailsTitle = MutableLiveData<String>().apply {
        value = "Технические детали:"
    }
    val technicalDetailsTitle: LiveData<String> = _technicalDetailsTitle

    private val _technicalDetails = MutableLiveData<String>().apply {
        value = """
            - Использование сенсоров: Приложение использует SensorManager и сенсоры типа Sensor.TYPE_LIGHT для считывания данных об освещенности.
            - Обновление данных: Данные об освещенности обновляются каждые 100 мс и сохраняются в памяти для последующего анализа.
            - Отображение данных: Графическое отображение данных реализовано с помощью пользовательского класса GraphView, который рисует график изменений освещенности.
        """.trimIndent()
    }
    val technicalDetails: LiveData<String> = _technicalDetails

    private val _developmentFeaturesTitle = MutableLiveData<String>().apply {
        value = "Особенности разработки:"
    }
    val developmentFeaturesTitle: LiveData<String> = _developmentFeaturesTitle

    private val _developmentFeatures = MutableLiveData<String>().apply {
        value = """
            - Совместимость: Приложение разработано для работы на всех современных устройствах Android с поддержкой сенсоров освещенности.
            - Производительность: Оптимизированное использование сенсоров и обновление данных в реальном времени без значительного влияния на производительность устройства.
        """.trimIndent()
    }
    val developmentFeatures: LiveData<String> = _developmentFeatures
}

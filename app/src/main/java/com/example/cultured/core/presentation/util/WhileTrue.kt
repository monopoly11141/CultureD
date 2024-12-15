package com.example.cultured.core.presentation.util

fun whileTrue(action: () -> Boolean) {
    while(action.invoke());
}
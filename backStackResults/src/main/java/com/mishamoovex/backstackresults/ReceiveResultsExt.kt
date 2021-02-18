package com.mishamoovex.backstackresults

import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController

inline fun <T> Fragment.registerForResult(
    key: String,
    crossinline resultEvent: (T) -> Unit
) {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(key)
        ?.observe(owner =  viewLifecycleOwner) {
            resultEvent.invoke(it)
        }
}

inline fun <T> Fragment.registerForResultOnce(
    key: String,
    crossinline resultEvent: (T) -> Unit
) {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(key)
        ?.observe(owner =  viewLifecycleOwner) {
            resultEvent.invoke(it)
            clearResultCallback<T>(key)
        }
}

fun <T> Fragment.clearResultCallback(key: String) {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.remove<T>(key)
}
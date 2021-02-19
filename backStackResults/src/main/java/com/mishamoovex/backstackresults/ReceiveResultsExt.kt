package com.mishamoovex.backstackresults

import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.observe
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

/**
 * Registers for result callbacks using the [NavController] [NavBackStackEntry] back stack
 * and [SavedStateHandle].
 *
 * Receives results while the lifecycle owner alive.
 *
 * @param key the key of the data request
 *
 * @param resultEvent the result callback with the data returned from the [sendResult]
 */
inline fun <T : Any> Fragment.registerForResult(
    key: String,
    crossinline resultEvent: (T) -> Unit
) {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(key)
        ?.observe(owner = viewLifecycleOwner) {
            resultEvent.invoke(it)
        }
}

/**
 * Registers for result callbacks using the [NavController] [NavBackStackEntry] back stack
 * and [SavedStateHandle].
 *
 * Receives result once. Unregistered automatically after getting result.
 *
 * @param key the key of the data request
 *
 * @param resultEvent the result callback with the data returned from the [sendResult]
 */
inline fun <T : Any> Fragment.registerForResultOnce(
    key: String,
    crossinline resultEvent: (T) -> Unit
) {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(key)
        ?.observe(owner = viewLifecycleOwner) {
            resultEvent.invoke(it)
            clearResultCallback<T>(key)
        }
}

/**
 * Unregisters result callback from the [NavBackStackEntry].
 *
 * @param key the key of the data request
 */
fun <T> Fragment.clearResultCallback(key: String) {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.remove<T>(key)
}
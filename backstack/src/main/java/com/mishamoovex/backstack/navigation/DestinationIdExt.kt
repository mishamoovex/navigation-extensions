package com.mishamoovex.backstack.navigation

import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun NavController.currentDestinationId(): Int = currentDestination?.id
    ?: throw IllegalStateException("Can't fetch current destinationId")

fun NavController.previousDestinationId(): Int = previousBackStackEntry?.destination?.id
    ?: throw IllegalArgumentException("Can't find previous screen on the back stack")

@ExperimentalCoroutinesApi
fun NavController.observeDestination(): Flow<Int> = callbackFlow {
    val navigationListener = NavController.OnDestinationChangedListener { _, destination, _ -> offer(destination.id) }
    addOnDestinationChangedListener(navigationListener)
    awaitClose { removeOnDestinationChangedListener(navigationListener) }
}
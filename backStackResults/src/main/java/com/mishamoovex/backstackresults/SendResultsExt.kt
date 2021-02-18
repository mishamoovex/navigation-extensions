package com.mishamoovex.backstackresults

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController

/**
 * Send result back using the [NavController] [NavBackStackEntry] back stack and [SavedStateHandle].
 *
 * By default it sends the [data] to the previous destination on the navigation back stack using
 * the [key] or  do nothing if less than two destinations on the stack.
 *
 * If we want to pop back stack to some arbitrary destination(if we have mote than 3 items on the back stack)
 * after sending result (not to the previous one) we should find that destination [NavBackStackEntry],
 * through destination ID, because [NavController.getPreviousBackStackEntry] will not send result
 * to the right destination in this case.
 *
 * @param destinationId any destination [IdRes] in the [NavGraph] to which we want send data back,
 *                      by default result will be sent to the previous destination if the
 *                      destination ID wasn't specified
 *
 * @param key the key to receive data via [registerForResult] or [registerForResultOnce] callbacks
 *
 * @param data the result data to return back to the caller
 */
fun <T: Any> Fragment.sendAsResult(
    @IdRes destinationId: Int? = null,
    key: String,
    data: T
) {
    //fetch backStackEntry as per the data destination
    val backStackEntry = with(findNavController()) {
        if (destinationId != null) {
            getBackStackEntry(destinationId)
        } else {
            previousBackStackEntry
        }
    }

    backStackEntry
        ?.savedStateHandle
        ?.set(key,data)
}
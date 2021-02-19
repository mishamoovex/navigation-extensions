package com.mishamoovex.backstack.navigation

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.mishamoovex.backstack.results.registerForResult
import com.mishamoovex.backstack.results.registerForResultOnce

/**
 * Navigates to the previous destination from the back stack.
 *
 * This method is designed for solving navigation problem which appears when  we are using
 * [registerForResult] or [registerForResultOnce] methods to start new destination to get some data
 * and return result back to the called destination.
 *
 * It gives us flexibility when we are designing a navigation graph. In this case we don't need
 * to create navigation actions from the result provider destination back to all callers.
 * We'll just specify a destination ID to return result and type screen invalidation
 * through [isPopupInclusive] value.
 *
 * This method works with combination [NavController.navigate] and [NavController.popBackStack] methods.
 *
 * If the [isPopupInclusive] value -> TRUE  NavController will pop up all destinations from the
 * back stack including the caller destination and create new caller screen (restart flow),
 *
 * If the [isPopupInclusive] value -> FALSE NavController will just pop up the back stack to the caller
 * destination without recreating it like in the previous case.
 *
 * @throws IllegalArgumentException if less than two screens in the NavController back stack
 */
fun NavController.popUpToCaller(
    isPopupInclusive: Boolean = false,
    @IdRes arbitraryDestination: Int? = null
) {
    val previousDestination = arbitraryDestination
    //If arbitrary destination wasn't specified fetch id of the previous screen
        ?: (previousBackStackEntry?.destination?.id
            ?: throw IllegalArgumentException("Can't find previous screen on the back stack"))

    if (isPopupInclusive) {
        //if inclusive is TRUE - pop up all flow from whe back stack and restart it
        val options = navOptions {
            popUpTo(previousDestination) {
                inclusive = true
            }
        }

        navigate(previousDestination, null, options)
    } else {
        //if inclusive - FALSE will just pop screen to the caller
        popBackStack(previousDestination, false)
    }
}
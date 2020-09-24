package com.hugo.test.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.storage.ParkingStorageImp


/**
 * This method create and return the [ParkingStorage]
 * implementation using the activity [Context].
 * @return [ParkingStorage].
 */
fun Activity.getParkingStorage(): ParkingStorage {
    return applicationContext.getParkingStorage()
}

/**
 * This method create and return the [ParkingStorage]
 * implementation using the fragment [Context].
 * @return [ParkingStorage].
 */
fun Fragment.getParkingStorage(): ParkingStorage {
    return requireContext().getParkingStorage()
}


/**
 * This method create and return the [ParkingStorage]
 * implementation using current [Context].
 * @return [ParkingStorage].
 */
fun Context.getParkingStorage(): ParkingStorage {
    return ParkingStorageImp(this)
}



/**
 * This method creates and returns an instance of [T] class that must
 * inherit from [ViewModel].
 *
 * When class [T] needs parameters to instantiate, the [creator]
 * lambda must be used (getViewModel { UserViewModel(userId) }).
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {

    return creator?.let {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    } ?: ViewModelProvider(this).get(T::class.java)
}

/**
 * This method creates and returns an instance of [T] class that must
 * inherit from [ViewModel].
 *
 * When class [T] needs parameters to instantiate, the [creator]
 * lambda must be used (getViewModel { UserViewModel(userId) }).
 */
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {

    return creator?.let {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    } ?: ViewModelProvider(this).get(T::class.java)
}


fun Activity.openActivity(
    activityClass: Class<*>,
    clearStack: Boolean = false,
    clearTop: Boolean = false,
    extras: Bundle? = null
) {

    val intent = Intent(this, activityClass)

    if (clearStack) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    if (clearTop) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    extras?.let { intent.putExtras(extras) }
    startActivity(intent)
}

/**
 * This method create a intent to open a activity [T].
 * With [init] method you can add flags or extras to
 * the intent.
 */
inline fun <reified T : Activity> Activity.openActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

/**
 * This method create a intent to open a activity [T]
 * for result with [requestCode].
 * With [init] method you can add flags or extras to
 * the intent.
 */
inline fun <reified T : Activity> Activity.openActivity(requestCode: Int, noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivityForResult(intent, requestCode)
}

fun Activity.openActivity(clsName: String, init: Intent.() -> Unit = {}) {
    val cls = Class.forName(clsName)
    val intent = Intent(this, cls)
    intent.init()
    startActivity(intent)
}

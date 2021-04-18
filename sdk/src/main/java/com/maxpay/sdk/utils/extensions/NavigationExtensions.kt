package com.maxpay.sdk.utils.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.maxpay.sdk.R
import kotlin.reflect.KClass

internal fun AppCompatActivity.addFragmentToContainer(
    fragment: Fragment,
    @IdRes container: Int = R.id.frameLayoutContainer
) {
    supportFragmentManager.addFragmentToContainer(fragment, container)
}

internal fun AppCompatActivity.showFragment(
    fragment: Fragment,
    @IdRes container: Int = R.id.frameLayoutContainer
) {
    supportFragmentManager.showFragmentToContainer(fragment, container)
}

internal fun AppCompatActivity.addFragmentToContainerWithoutBackStack(
    fragment: Fragment,
    @IdRes container: Int = R.id.frameLayoutContainer
) {
    supportFragmentManager.addFragmentToContainerWithoutBackStack(fragment, container)
}

internal fun AppCompatActivity.clearBackStack(kClass: KClass<out Fragment>? = null) {
    supportFragmentManager.clearBackStack(kClass)
}

internal fun FragmentActivity.clearBackStack(kClass: KClass<out Fragment>? = null) {
    supportFragmentManager.clearBackStack(kClass)
}

internal fun Fragment.addFragmentToContainer(
    fragment: Fragment,
    @IdRes container: Int,
    fragmentManager: FragmentManager? = null
) {
    fragmentManager?.addFragmentToContainer(fragment, container)
        ?: this.fragmentManager?.addFragmentToContainer(fragment, container)
}

internal fun Fragment.addFragmentToContainerWithoutBackStack(
    fragment: Fragment,
    @IdRes container: Int,
    fragmentManager: FragmentManager? = null
) {
    fragmentManager?.addFragmentToContainerWithoutBackStack(fragment, container)
        ?: this.fragmentManager?.addFragmentToContainerWithoutBackStack(fragment, container)
}

internal fun Fragment.clearBackStack(
    kClass: KClass<out Fragment>? = null,
    fragmentManager: FragmentManager? = null
) {
    fragmentManager?.clearBackStack(kClass)
        ?: this.fragmentManager?.clearBackStack(kClass)
}

internal fun FragmentManager.addFragmentToContainer(fragment: Fragment, @IdRes container: Int) {
    this.beginTransaction()
        .replace(container, fragment, fragment::class.java.name)
        .addToBackStack(fragment::class.java.name)
        .commit()
}

internal fun FragmentManager.showFragmentToContainer(fragment: Fragment, @IdRes container: Int) {
    this.beginTransaction()
        .add(container, fragment, fragment::class.java.name)
        .addToBackStack(fragment::class.java.name)
        .commit()
}

internal fun FragmentManager.addFragmentToContainerWithoutBackStack(
    fragment: Fragment,
    @IdRes container: Int
) {
    this.beginTransaction()
        .replace(container, fragment, fragment::class.java.name)
        .commit()
}

internal fun FragmentManager.clearBackStack(kClass: KClass<out Fragment>? = null) {
    this.popBackStack(kClass?.java?.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}
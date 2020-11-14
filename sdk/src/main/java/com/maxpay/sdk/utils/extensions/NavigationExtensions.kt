package com.maxpay.sdk.utils.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.maxpay.sdk.R
import kotlin.reflect.KClass

fun AppCompatActivity.addFragmentToContainer(
    fragment: Fragment,
    @IdRes container: Int = R.id.frameLayoutContainer
) {
    supportFragmentManager.addFragmentToContainer(fragment, container)
}

fun AppCompatActivity.showFragment(
    fragment: Fragment,
    @IdRes container: Int = R.id.frameLayoutContainer
) {
    supportFragmentManager.showFragmentToContainer(fragment, container)
}

fun AppCompatActivity.addFragmentToContainerWithoutBackStack(
    fragment: Fragment,
    @IdRes container: Int = R.id.frameLayoutContainer
) {
    supportFragmentManager.addFragmentToContainerWithoutBackStack(fragment, container)
}

fun AppCompatActivity.clearBackStack(kClass: KClass<out Fragment>? = null) {
    supportFragmentManager.clearBackStack(kClass)
}

fun FragmentActivity.clearBackStack(kClass: KClass<out Fragment>? = null) {
    supportFragmentManager.clearBackStack(kClass)
}

fun Fragment.addFragmentToContainer(
    fragment: Fragment,
    @IdRes container: Int,
    fragmentManager: FragmentManager? = null
) {
    fragmentManager?.addFragmentToContainer(fragment, container)
        ?: this.fragmentManager?.addFragmentToContainer(fragment, container)
}

fun Fragment.addFragmentToContainerWithoutBackStack(
    fragment: Fragment,
    @IdRes container: Int,
    fragmentManager: FragmentManager? = null
) {
    fragmentManager?.addFragmentToContainerWithoutBackStack(fragment, container)
        ?: this.fragmentManager?.addFragmentToContainerWithoutBackStack(fragment, container)
}

fun Fragment.clearBackStack(
    kClass: KClass<out Fragment>? = null,
    fragmentManager: FragmentManager? = null
) {
    fragmentManager?.clearBackStack(kClass)
        ?: this.fragmentManager?.clearBackStack(kClass)
}

fun FragmentManager.addFragmentToContainer(fragment: Fragment, @IdRes container: Int) {
    this.beginTransaction()
        .replace(container, fragment, fragment::class.java.name)
        .addToBackStack(fragment::class.java.name)
        .commit()
}

fun FragmentManager.showFragmentToContainer(fragment: Fragment, @IdRes container: Int) {
    this.beginTransaction()
        .add(container, fragment, fragment::class.java.name)
        .addToBackStack(fragment::class.java.name)
        .commit()
}

fun FragmentManager.addFragmentToContainerWithoutBackStack(
    fragment: Fragment,
    @IdRes container: Int
) {
    this.beginTransaction()
        .replace(container, fragment, fragment::class.java.name)
        .commit()
}

fun FragmentManager.clearBackStack(kClass: KClass<out Fragment>? = null) {
    this.popBackStack(kClass?.java?.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}
/*
 * Copyright (c) 2024, Wultra s.r.o. (www.wultra.com).
 *
 * All rights reserved. This source code can be used only for purposes specified
 * by the given license contract signed by the rightful deputy of Wultra s.r.o.
 * This source code can be used only by the owner of the license.
 *
 * Any disputes arising in respect of this agreement (license) shall be brought
 * before the Municipal Court of Prague.
 */

package com.wultra.android.utilities

/**
 * A Java-enabled wrapper for the kotlin.Result class which is unable to bridge into the Java.
 *
 * Example usage:
 * ```
 * // imagine having a 3rd party service that is providing a list of users asynchronously
 * // with Result-like based API, that returns kotlin.Result object via lambda function you provided
 *
 * myService.fetchUsers( result -> {
 *     new JResult<List<User>>(result)
 *         .onFailure( exception -> {
 *             // failed to retrieve the list
 *             // process exception
 *             return null;
 *         })
 *         .onSuccess( users -> {
 *              // process users of List<User> type
 *              return null
 *         });
 *     return null; // end of fetchUsers
 * });
 * ```
 */
@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class JResult<T>(result: Result<T>) {

    companion object {
        /**
         * Creates JResult with the given result.
         *
         * @param T success type
         * @param result original result object
         * @return JResult
         */
        @JvmStatic
        fun <T>wrap(result: Result<T>): JResult<T> = JResult(result)
    }

    private val unboxed = result.unbox()

    /** Returns true if this instance represents a successful outcome. In this case isFailure returns false. */
    val isSuccess = unboxed != null && unboxed !is Throwable

    /** Returns true if this instance represents a failed outcome. In this case isSuccess returns false. */
    val isFailure = unboxed == null || unboxed is Throwable

    /** Returns the encapsulated value if this instance represents success or null if it is failure. */
    fun getOrNull(): T? = if (isSuccess) unboxed as? T else null

    /** Returns the value if this instance represents success or throws an exception if it is failure. */
    fun getOrThrow(): T = if (isFailure) throw exceptionOrNull()!! else unboxed as T

    /** Returns the encapsulated Throwable exception if this instance represents failure or null if it is success. */
    fun exceptionOrNull(): Throwable? {
        return if (isFailure) {
            unboxed as? Throwable ?: Throwable("Failed to unbox the value")
        } else {
            null
        }
    }

    override fun toString() = "JResult wrapper of $unboxed"

    /** Performs the given action on the encapsulated exception if this instance represents failure. Returns the original Result unchanged. */
    fun onFailure(action: (exception: Throwable) -> Void): JResult<T> {
        exceptionOrNull()?.let { action(it) }
        return this
    }

    /** Performs the given action on the encapsulated value if this instance represents success. Returns the original Result unchanged. */
    fun onSuccess(action: (value: T) -> Void): JResult<T> {
        getOrNull()?.let { action(it) }
        return this
    }

    @Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
    private fun <T> Result<T>.unbox(): Any? {
        var unboxed: Any? = this
        while (unboxed is Result<*> || unboxed is Result.Failure) {
            unboxed = (unboxed as? Result<*>)?.value ?: (unboxed as? Result.Failure)?.exception
        }
        return unboxed
    }
}
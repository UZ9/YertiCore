package com.yerti.core.menus

import java.util.*

class Pagination<T> @JvmOverloads constructor(private val pageSize: Int, objects: List<T>? = ArrayList()) : ArrayList<T>() {

    @SafeVarargs
    constructor(pageSize: Int, vararg objects: T) : this(pageSize, Arrays.asList(*objects)) {
    }

    fun pageSize(): Int {
        return pageSize
    }

    fun totalPages(): Int {
        return Math.ceil(size.toDouble() / pageSize).toInt()
    }

    fun exists(page: Int): Boolean {
        return page >= 0 && page < totalPages()
    }

    fun getPage(page: Int): List<T?> {
        if (page < 0 || page >= totalPages()) throw IndexOutOfBoundsException("Index: " + page + ", Size: " + totalPages())
        val objects: MutableList<T?> = ArrayList()
        val min = page * pageSize
        var max = page * pageSize + pageSize
        if (max > size) max = size
        var i = min
        while (max > i) {
            objects.add(get(i))
            i++
        }
        return objects
    }

    init {
        addAll(objects!!)
    }
}
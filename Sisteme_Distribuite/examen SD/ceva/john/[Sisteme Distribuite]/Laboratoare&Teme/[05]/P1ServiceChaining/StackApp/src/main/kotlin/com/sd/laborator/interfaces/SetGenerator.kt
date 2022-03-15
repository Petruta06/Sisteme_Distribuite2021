package com.sd.laborator.interfaces


import com.sd.laborator.model.Stack

interface SetGenerator {
    fun setGenerate(length : Int) : Stack
}
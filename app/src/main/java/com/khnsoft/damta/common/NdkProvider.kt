package com.khnsoft.damta.common

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NdkProvider @Inject constructor() {

    external fun getRestApiKey(): String

    companion object {
        init {
            System.loadLibrary("damta")
        }
    }
}

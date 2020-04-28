package com.silverstar.sampleapp.business.base

import io.reactivex.ObservableTransformer

interface ProcessorHolder<U, D> {

    val processor: ObservableTransformer<U, D>
}
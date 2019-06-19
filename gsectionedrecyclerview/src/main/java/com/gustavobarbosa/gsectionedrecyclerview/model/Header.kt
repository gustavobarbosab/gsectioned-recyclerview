package com.gustavobarbosa.gsectionedrecyclerview.model

interface Header<BODY> {
    fun getListBody(): List<BODY>?
}
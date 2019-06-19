package com.gustavobarbosa.recyclerviewsectioned.lib

interface Header<BODY> {
    fun getListBody(): List<BODY>?
}
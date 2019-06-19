package com.gustavobarbosa.recyclerviewsectioned.lib.model

interface Header<BODY> {
    fun getListBody(): List<BODY>?
}
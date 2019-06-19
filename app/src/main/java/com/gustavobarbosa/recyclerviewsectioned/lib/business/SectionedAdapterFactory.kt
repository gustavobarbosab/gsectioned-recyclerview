package com.gustavobarbosa.recyclerviewsectioned.lib.business

import com.gustavobarbosa.recyclerviewsectioned.lib.model.Header

object SectionedAdapterFactory {
    fun <HEADER_MODEL : List<Header<*>>>createSectionedAdapterManager():  SectionedAdapterManager<HEADER_MODEL> =
        SectionedAdapterManagerImpl()
}
package com.gustavobarbosa.recyclerviewsectioned.lib.business

object SectionedAdapterFactory {
    fun createSectionedAdapterManager(listener: SectionedAdapterManager.SectionedManagerListener)
        : SectionedAdapterManager =
        SectionedAdapterManagerImpl(listener)
}
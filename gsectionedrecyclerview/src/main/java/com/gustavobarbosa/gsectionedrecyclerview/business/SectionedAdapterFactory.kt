package com.gustavobarbosa.gsectionedrecyclerview.business

object SectionedAdapterFactory {
    fun createSectionedAdapterManager(listener: SectionedAdapterManager.SectionedManagerListener)
        : SectionedAdapterManager =
        SectionedAdapterManagerImpl(listener)
}
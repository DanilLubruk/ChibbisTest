package com.example.chibbistest.data.repositories

import com.example.chibbistest.data.services.HitsService

class HitsRepository constructor(private val hitsService: HitsService) {
    suspend fun getAllHits() = hitsService.getAllHits()
}
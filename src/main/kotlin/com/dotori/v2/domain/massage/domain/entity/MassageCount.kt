package com.dotori.v2.domain.massage.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "massage_count")
class MassageCount(
    @Id
    val id: Long = 1
) {
    @Column(name = "massage_count")
    var count: Long = 0
    private set

    fun addCount() {
        count = count!! + 1
    }

    fun deductionCount() {
        count = count!! - 1
    }

    fun clearCount() {
        count = 0L
    }
}
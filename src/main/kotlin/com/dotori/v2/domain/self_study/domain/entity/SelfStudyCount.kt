package com.dotori.v2.domain.self_study.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "SelfStudyCount")
class SelfStudyCount(
    @Id
    @Column(name = "selfStudyCount_id")
    val id: Long = 1
) {
    @Column(name = "selfStudyCount_count")
    var count: Long = 0
    private set

    fun addCount() {
        count += 1
    }

    fun removeCount() {
        count -= 1
    }

    fun updateCount(count: Long?) {
        this.count = count ?: this.count
    }
}
package com.dotori.v2.domain.selfstudy.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "self_study_count")
class SelfStudyCount(
    @Id
    @Column(name = "selfStudyCount_id")
    val id: Long = 1,
    count: Long = 0,
    limit: Int = 50
) {
    @Column(name = "selfStudyCount_count")
    var count: Long = count
    private set

    @Column(name = "selfStudyCount_limit")
    var limit: Int = limit
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

    fun updateLimit(limit: Int){
        this.limit = limit
    }
}
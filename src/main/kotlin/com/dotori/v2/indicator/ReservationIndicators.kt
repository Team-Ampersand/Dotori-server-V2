package com.dotori.v2.indicator

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import java.time.LocalDateTime

@Entity
class ReservationIndicators(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    val userId: Long,

    val requestTime: LocalDateTime,

    val responseTime: LocalDateTime,

    val latencyMs: Long,

    val resultStatus: ResultStatus,

    val reservationCategory: ReservationCategory
)
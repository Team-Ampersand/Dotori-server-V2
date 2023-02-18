package com.dotori.v2.global.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    val createdDate: LocalDateTime? = null

    @LastModifiedDate
    val modifiedDate: LocalDateTime? = null
}
package com.dotori.v2.domain.student.service

import com.dotori.v2.domain.student.presentation.data.req.ModifyStudentInfoRequest

interface ModifyStudentInfoService {
    fun execute(modifyStudentInfoRequest: ModifyStudentInfoRequest)
}
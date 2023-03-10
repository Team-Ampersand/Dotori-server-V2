package com.dotori.v2.domain.stu_info.service

import com.dotori.v2.domain.stu_info.presentation.data.req.ModifyStudentInfoRequest

interface ModifyStudentInfoService {
    fun execute(modifyStudentInfoRequest: ModifyStudentInfoRequest)
}
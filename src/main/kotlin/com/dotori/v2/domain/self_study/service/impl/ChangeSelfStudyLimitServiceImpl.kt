package com.dotori.v2.domain.self_study.service.impl

import com.dotori.v2.domain.self_study.presentation.dto.req.SelfStudyLimitReqDto
import com.dotori.v2.domain.self_study.service.ChangeSelfStudyLimitService
import com.dotori.v2.domain.self_study.util.FindSelfStudyCountUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class ChangeSelfStudyLimitServiceImpl(
    private val findSelfStudyCountUtil: FindSelfStudyCountUtil
) : ChangeSelfStudyLimitService {
    override fun execute(selfStudyLimitReqDto: SelfStudyLimitReqDto) {
        val selfStudyCount = findSelfStudyCountUtil.findSelfStudyCount()
        selfStudyCount.updateLimit(selfStudyLimitReqDto.limit)
    }
}
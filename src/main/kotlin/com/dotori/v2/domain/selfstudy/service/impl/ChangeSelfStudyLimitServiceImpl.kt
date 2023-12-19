package com.dotori.v2.domain.selfstudy.service.impl

import com.dotori.v2.domain.selfstudy.presentation.dto.req.SelfStudyLimitReqDto
import com.dotori.v2.domain.selfstudy.service.ChangeSelfStudyLimitService
import com.dotori.v2.domain.selfstudy.util.FindSelfStudyCountUtil
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
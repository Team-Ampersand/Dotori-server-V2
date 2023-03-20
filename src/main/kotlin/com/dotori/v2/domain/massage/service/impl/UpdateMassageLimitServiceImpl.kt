package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.presentation.dto.req.MassageLimitReqDto
import com.dotori.v2.domain.massage.service.UpdateMassageLimitService
import com.dotori.v2.domain.massage.util.FindMassageCountUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class UpdateMassageLimitServiceImpl(
    private val massageCountUtil: FindMassageCountUtil
) : UpdateMassageLimitService {
    override fun execute(massageLimitReqDto: MassageLimitReqDto) {
        val massageCount = massageCountUtil.findMassageCount()
        massageCount.updateLimit(massageLimitReqDto.limit)
    }
}
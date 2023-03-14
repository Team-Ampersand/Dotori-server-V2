package com.dotori.v2.domain.massage.service.impl

import com.dotori.v2.domain.massage.domain.repository.MassageRepository
import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberListResDto
import com.dotori.v2.domain.massage.presentation.dto.res.MassageMemberResDto
import com.dotori.v2.domain.massage.service.GetMassageRankService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetMassageRankServiceImpl(
    private val massageRepository: MassageRepository
) : GetMassageRankService {
    override fun execute(): MassageMemberListResDto =
        MassageMemberListResDto(
            massageRepository.findAllOrderByCreatedDateAsc()
                .mapIndexed { index, massage ->
                    MassageMemberResDto(index + 1L, massage.member)
                }
        )
}
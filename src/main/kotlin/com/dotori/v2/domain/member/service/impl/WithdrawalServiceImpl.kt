package com.dotori.v2.domain.member.service.impl

import com.dotori.v2.domain.member.domain.repository.MemberRepository
import com.dotori.v2.domain.member.exception.MemberNotFoundException
import com.dotori.v2.domain.member.exception.MemberNotSameException
import com.dotori.v2.domain.member.exception.PasswordMismatchException
import com.dotori.v2.domain.member.presentation.data.req.WithdrawalReqDto
import com.dotori.v2.domain.member.service.WithdrawalService
import com.dotori.v2.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WithdrawalServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userUtil: UserUtil,
) : WithdrawalService {
    override fun execute(withdrawalReqDto: WithdrawalReqDto) {
        val currentUser = userUtil.fetchCurrentUser()

        val member = memberRepository.findByEmail(withdrawalReqDto.email)
            ?: throw MemberNotFoundException()

        if (!passwordEncoder.matches(withdrawalReqDto.password, member.password))
            throw PasswordMismatchException()

        if (currentUser != member)
            throw MemberNotSameException()

        memberRepository.delete(member)
    }
}
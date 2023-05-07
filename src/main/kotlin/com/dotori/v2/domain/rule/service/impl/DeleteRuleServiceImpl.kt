package com.dotori.v2.domain.rule.service.impl

import com.dotori.v2.domain.rule.domain.repository.RuleRepository
import com.dotori.v2.domain.rule.exception.NotFoundRuleException
import com.dotori.v2.domain.rule.service.DeleteRuleService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteRuleServiceImpl(
    private val ruleRepository: RuleRepository
) : DeleteRuleService {
    override fun execute(ruleId: Long) =
        ruleRepository.findByIdOrNull(ruleId)
            .let { if (it == null) throw NotFoundRuleException() else ruleRepository.delete(it) }
}
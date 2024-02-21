package com.dotori.v2.domain.member.domain.entity

import com.dotori.v2.domain.member.enums.*
import com.dotori.v2.domain.rule.domain.entity.RuleViolation
import com.dotori.v2.global.entity.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0,

    @Column(name = "member_name", nullable = false)
    val memberName: String,

    @Column(name = "member_stu_num", nullable = false)
    var stuNum: String,

    @Column(name = "member_email", nullable = false, unique = true)
    val email: String,

    password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "member_gender")
    val gender: Gender,

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = [JoinColumn(name = "member_id")])
    val roles: MutableList<Role>,

    @OneToMany(mappedBy = "member")
    val ruleViolation: MutableList<RuleViolation>,

    @Column(name = "profile_image")
    var profileImage: String?

) : BaseTimeEntity() {

    @Column(name = "member_point")
    val point: Long = 0

    @Column(name = "self_study_check")
    var selfStudyCheck = false
        private set

    @Column(name = "member_password", nullable = false)
    var password: String = password
        private set

    @Column(name = "self_study_expired_date")
    var selfStudyExpiredDate: LocalDateTime? = null
        private set

    @Enumerated(EnumType.STRING)
    @Column(name = "member_selfstudy", nullable = false)
    var selfStudyStatus: SelfStudyStatus = SelfStudyStatus.CAN
        private set

    @Enumerated(EnumType.STRING)
    @Column(name = "member_music", nullable = false)
    var musicStatus: MusicStatus = MusicStatus.CAN
        private set

    @Enumerated(EnumType.STRING)
    @Column(name = "member_massage", nullable = false)
    var massageStatus: MassageStatus = MassageStatus.CAN
        private set

    fun updatePassword(newPassword: String) {
        this.password = newPassword
    }

    fun updateSelfStudyStatus(selfStudyStatus: SelfStudyStatus) {
        this.selfStudyStatus = selfStudyStatus
    }

    fun updateSelfStudyCheck(check: Boolean) {
        this.selfStudyCheck = check
    }

    fun updateMusicStatus(musicStatus: MusicStatus) {
        this.musicStatus = musicStatus
    }

    fun updateMassageStatus(massageStatus: MassageStatus) {
        this.massageStatus = massageStatus
    }

    fun updateSelfStudyExpiredDate(localDateTime: LocalDateTime?) {
        this.selfStudyExpiredDate = localDateTime
    }

    fun updateProfileImage(profileImage: String?): Member {
        return Member(
            id = this.id,
            memberName = this.memberName,
            password = this.password,
            stuNum = this.stuNum,
            email = this.email,
            gender = this.gender,
            roles = this.roles,
            ruleViolation = this.ruleViolation,
            profileImage = profileImage
        )
    }

    fun graduate(period: String) {
        this.stuNum = period
    }
}
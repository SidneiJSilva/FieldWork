package com.example.tjsid.fieldwork.business

import android.content.Context
import com.example.tjsid.fieldwork.entities.UserEntity
import com.example.tjsid.fieldwork.repository.UserRepository

class UserBusiness(context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)

    fun get(userEntity: UserEntity) = mUserRepository.get(userEntity)

    fun insert(userEntity: UserEntity) = mUserRepository.insert(userEntity)

    fun getToVerify() = mUserRepository.getToVerify()

    fun getUserList() = mUserRepository.getUserList()

}
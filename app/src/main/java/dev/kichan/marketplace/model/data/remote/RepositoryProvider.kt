package dev.kichan.marketplace.model.data.remote

import dev.kichan.marketplace.model.repository.MembersRepository
import dev.kichan.marketplace.model.services.MembersService

object RepositoryProvider {

    private val retrofit = RetrofitClient.getClient()
    private val membersService = retrofit.create(MembersService::class.java)

    fun provideMembersRepository(): MembersRepository {
        return MembersRepository(membersService)
    }
}

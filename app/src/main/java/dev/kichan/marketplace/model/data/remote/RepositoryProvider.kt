package dev.kichan.marketplace.model.data.remote

import dev.kichan.marketplace.model.repository.CouponsRepository
import dev.kichan.marketplace.model.repository.MembersRepository
import dev.kichan.marketplace.model.services.CouponsService
import dev.kichan.marketplace.model.services.MembersService

object RepositoryProvider {

    private val retrofit = RetrofitClient.getClient()
    private val membersService = retrofit.create(MembersService::class.java)
    private val couponsService = retrofit.create(CouponsService::class.java)

    fun provideMembersRepository(): MembersRepository {
        return MembersRepository(membersService)
    }

    fun provideCouponsRepository(): CouponsRepository {
        return CouponsRepository(couponsService)
    }
}

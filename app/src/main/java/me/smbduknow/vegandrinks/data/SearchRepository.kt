package me.smbduknow.vegandrinks.data

import io.reactivex.Single
import io.reactivex.rxkotlin.flatMapIterable
import io.reactivex.rxkotlin.toObservable
import me.smbduknow.vegandrinks.data.model.Product

class SearchRepository {

    private val source = RemoteDataSource

    fun search(query: String): Single<List<Product>> =
        source.getSearchResults(query)
            .toObservable()
            .flatMapIterable()
            .flatMapSingle { source.getCompanyDetails(it.id) }
            .flatMap { company ->
                company.products
                    .filter { it.product_name.contains(query, ignoreCase = true) }
                    .toObservable()
            }
            .toList()
}
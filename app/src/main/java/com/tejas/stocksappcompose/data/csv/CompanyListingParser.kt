package com.tejas.stocksappcompose.data.csv

import com.opencsv.CSVReader
import com.tejas.stocksappcompose.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyListingParser @Inject constructor(): CSVParser<CompanyListing> {

    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull {line->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    val assetType = line.getOrNull(3)
                    val ipoDate = line.getOrNull(4)
                    val delistingDate = line.getOrNull(5)
                    val status = line.getOrNull(6)
                    CompanyListing(
                        name = name?: return@mapNotNull null,
                        symbol = symbol?: return@mapNotNull null,
                        exchange = exchange?: return@mapNotNull null,
                        assetType = assetType?: return@mapNotNull null,
                        ipoDate = ipoDate?: return@mapNotNull null,
                        delistingDate = delistingDate?: return@mapNotNull null,
                        status = status?: return@mapNotNull null
                    )
                }
                .also{
                    csvReader.close()
                }
        }
    }

}
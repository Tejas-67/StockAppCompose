package com.tejas.stocksappcompose.presentation.company_listings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CompanyListingsScreen(
    viewModel: CompanyListingsViewModel = hiltViewModel()
){
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefresh
    )
    val state = viewModel.state
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    CompanyListingsEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text(text = "Search....")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(
                    CompanyListingsEvent.Refresh
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.companies.size){ index->
                    val company = state.companies[index]
                    CompanyItem(
                        company = company,
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
                            }
                            .padding(16.dp)
                    )
                    if(index<state.companies.size){
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }

}

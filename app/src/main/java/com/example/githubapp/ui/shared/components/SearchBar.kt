package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.githubapp.R
import com.example.githubapp.ui.theme.GithubGray
import com.example.githubapp.ui.theme.LightGray

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var showClearIcon by remember { mutableStateOf(searchQuery.isNotEmpty()) }
    showClearIcon = searchQuery.isNotEmpty()

    TextField(
        value = searchQuery,
        onValueChange = {
            onQueryChange.invoke(it)
        },
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Sharp.Search,
                contentDescription = stringResource(id = R.string.search_icon),
                tint = GithubGray,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.search_icon_padding))
                    .size(dimensionResource(id = R.dimen.search_icon_size))
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.search_bar_border_radius)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = LightGray,
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent
        ),
        placeholder = {
            Text(text = stringResource(id = R.string.search))
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { onQueryChange.invoke("") }) {
                    Icon(
                        imageVector = Icons.Sharp.Clear,
                        tint = GithubGray,
                        contentDescription = stringResource(id = R.string.search_clear_icon)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

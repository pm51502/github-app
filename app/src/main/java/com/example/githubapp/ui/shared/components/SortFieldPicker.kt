package com.example.githubapp.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import com.example.githubapp.R
import com.example.githubapp.ui.theme.GithubGray
import com.example.githubapp.ui.theme.LightGray
import java.util.*

enum class SortField {
    STARS,
    FORKS,
    UPDATED
}

@Composable
fun SortFieldPicker(
    modifier: Modifier = Modifier,
    options: List<SortField>,
    onOptionSelect: (SortField) -> Unit,
    selectedOption: SortField? = null,
) {
    Layout(
        modifier = modifier
            .clip(shape = RoundedCornerShape(percent = 50))
            .background(color = LightGray),
        content = {
            options.forEach { option ->
                val backgroundColor = if (option == selectedOption) GithubGray else LightGray
                val contentColor = if (option == selectedOption) White else GithubGray

                Box(
                    modifier = Modifier
                        .clickable { onOptionSelect(option) }
                        .background(color = backgroundColor),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option.toString().lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.body1,
                        color = contentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.filter_picker_item_padding)
                        ),
                    )
                }
            }
        }
    ) { measurables, constraints ->
        val optionWidth = constraints.maxWidth / options.size
        val optionConstraints = Constraints.fixed(
            width = optionWidth,
            height = constraints.maxHeight,
        )
        val optionPlaceables =
            measurables.map { measurable -> measurable.measure(optionConstraints) }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            optionPlaceables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = optionWidth * index,
                    y = 0,
                )
            }
        }
    }
}

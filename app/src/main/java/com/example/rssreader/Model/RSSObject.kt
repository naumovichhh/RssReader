package com.example.rssreader.Model

data class RSSObject(
    val status: String,
    val feed: Feed,
    val items: List<Item>
)
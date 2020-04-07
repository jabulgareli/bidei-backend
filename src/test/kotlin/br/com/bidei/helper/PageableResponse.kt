package br.com.bidei.helper

import org.springframework.data.domain.Page

class PageableResponse<T> (
        val content: MutableList<T>
)
package com.jonichi.soc.dto.V1;

import java.math.BigDecimal;

public record CoursePriceDtoV1(
        BigDecimal amount,
        String currencyCode
) {
}

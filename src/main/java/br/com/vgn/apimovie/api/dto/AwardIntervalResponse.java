package br.com.vgn.apimovie.api.dto;

import java.util.List;

public record AwardIntervalResponse(
        List<AwardIntervalItemResponse> min,
        List<AwardIntervalItemResponse> max
) {}

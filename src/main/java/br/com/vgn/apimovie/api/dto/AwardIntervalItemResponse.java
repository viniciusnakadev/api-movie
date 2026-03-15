package br.com.vgn.apimovie.api.dto;

public record AwardIntervalItemResponse(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {}

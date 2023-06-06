package com.cesarFrancisco.votePage.exceptions;

public record StandardError(Long timestamp, Integer status, String error, String path, String message) {
}

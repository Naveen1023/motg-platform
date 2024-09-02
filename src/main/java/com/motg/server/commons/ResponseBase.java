package com.motg.server.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBase<T> {

  private String status;
  private T data;
  private String message;

  public static <T> ResponseBase<T> successMapper(T data, String message) {
    return ResponseBase.<T>builder()
      .status("success")
      .data(data)
      .message(message)
      .build();
  }

  public static <T> ResponseBase<T> failureMapper(T data, String message) {
    return ResponseBase.<T>builder()
      .status("failure")
      .data(data)
      .message(message)
      .build();
  }
}

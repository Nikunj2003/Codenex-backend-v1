package com.nikunj.codenex.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.nikunj.codenex.enums.MessageRole;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {
    Long id;
    ChatSession chatSession;
    MessageRole role;
    String content;
    String toolCalls; // JSON Array string
    String toolCallId;
    Integer tokensUsed;
    Instant createdAt;
}

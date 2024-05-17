package com.salmon.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息请求
 *
 * @author Salmon
 * @since 2024-05-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageReq {

    private String content;

    private Long roomId;

    private Long replyMsgId;
}

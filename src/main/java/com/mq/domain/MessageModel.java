package com.mq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 开发公司：联信
 * 版权：联信
 * <p>
 * Annotation
 *
 * @author 刘志强
 * @created Create Time: 2021/3/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel<T extends Serializable> implements Serializable {
    private String message;

    private String title;

    private T data;
}

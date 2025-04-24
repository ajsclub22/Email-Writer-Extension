package com.email.writer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailBody {
    private String content, tone;

    @Override
    public String toString() {
        return "EmailBody{" +
                "content='" + content + '\'' +
                ", tone='" + tone + '\'' +
                '}';
    }
}

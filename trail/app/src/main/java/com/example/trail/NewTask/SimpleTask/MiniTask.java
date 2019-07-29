package com.example.trail.NewTask.SimpleTask;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniTask implements Serializable {
    private String content;
    private Boolean done;
}

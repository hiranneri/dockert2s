package br.com.docker.t2s.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Regex {
    private String regex;
}

package br.com.workshop.demo.camelconsumerproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cacheable {

    private Object object;
    private String key;
    private Integer period;
    private TimeUnit timeUnit;
}

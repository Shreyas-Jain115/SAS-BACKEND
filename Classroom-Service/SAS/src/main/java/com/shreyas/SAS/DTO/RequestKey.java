package com.shreyas.SAS.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestKey implements Serializable {
    RequestType requestType;
    String value;

    public static RequestKey of(String userId, RequestType requestType) {
        return new RequestKey(requestType,userId);
    }

    @Override
    public String toString() {
        return "req:" + requestType.name() + "::user:" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestKey)) return false;
        RequestKey that = (RequestKey) o;
        return Objects.equals(value, that.value) &&
                requestType == that.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, requestType);
    }
}

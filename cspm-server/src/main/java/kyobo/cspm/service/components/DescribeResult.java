package kyobo.cspm.service.components;

import kyobo.cspm.enums.ResultType;

import java.util.List;

public class DescribeResult<T> {

    private final List<T> data;
    private final ResultType resultType;

    private DescribeResult(ResultType resultType, List<T> data) {
        this.data = data;
        this.resultType = resultType;
    }

    public static <T> DescribeResult<T> setResult(ResultType resultType, List<T> data) {
        return new DescribeResult<>(resultType, data);
    }

    public List<T> getData() {
        return data;
    }

    public ResultType getResultType() {
        return resultType;
    }
}